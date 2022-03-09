package top.kkoishi.xml.parse;

import top.kkoishi.xml.lang.IllegalXMLEscapeCharException;
import top.kkoishi.xml.lang.IllegalXMLFormatException;

import java.io.*;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public final class Tokenizer implements Serializable {
    final File xmlFile;

    String content;

    final List<Token> tokens = new LinkedList<>();

    private Tokenizer (File xmlFile, String content) {
        this.xmlFile = xmlFile;
        this.content = content;
    }

    public static Tokenizer getInstance (File f) {
        return new Tokenizer(f, "");
    }

    public synchronized void read () throws IOException {
        final StringBuilder sb = new StringBuilder();
        final BufferedInputStream bis = new BufferedInputStream(new FileInputStream(xmlFile));
        int len;
        byte[] buffer = new byte[4096];
        while ((len = bis.read(buffer)) != -1) {
            sb.append(new String(buffer, 0, len));
        }
        content = sb.toString();
        bis.close();
    }

    /**
     * <pre>
     * "->&quot;
     * &->&amp;
     * < ->&lt;
     * > ->&gt;
     * ' '->&nbsp;
     *      syntax:
     *          xml->&lt;root&gt;element&lt;/root&gt;
     *          element->&lt;root&gt;element%lt;/root&gt;
     *                 | text
     *                 | &lt;root/&gt;
     *                 | annotation
     *          annotation->&lt;?string?&gt;
     *          root->string_nn
     *              | string_nn entries
     *          entries-> entry entries
     *                 | entry
     *                 | null
     *          entry->string_nn="string"
     *          string->[char]*
     *                | string_nn
     *          string_nn->[char]+
     * </pre>
     */
    public synchronized void tokenize () throws IllegalXMLEscapeCharException, IllegalXMLFormatException {
        final char[] charArray = content.toCharArray();
        final int len = charArray.length;
        final LinkedList<String> stack = new LinkedList<>();
        final LinkedList<Token> temp = new LinkedList<>();
        final LinkedList<Token> entriesTemp = new LinkedList<>();
        boolean indexing = false;
        Token token = null;
        for (int i = 0; i < len; i++) {
            final char c = charArray[i];
            if (c == '&') {
                int endPos = i;
                while (endPos < len && charArray[endPos] != ';') {
                    ++endPos;
                }
                if (charArray[endPos] != ';') {
                    throw new IllegalXMLEscapeCharException();
                } else {
                    final StringBuilder sb = new StringBuilder();
                    for (int j = i; j < endPos; j++) {
                        sb.append(charArray[j]);
                    }
                    if (token != null) {
                        System.out.println("&" + sb);
                        token.content.append(sb);
                    } else {
                        token = new Token(Token.XML_OBJECT, sb.toString());
                    }
                    i = endPos + 1;
                }
            } else if (c == '>') {
                if (indexing) {
                    indexing = false;
                    if (charArray[i - 1] != '/') {
                        if (charArray[i - 1] == '?') {
                            for (Token t : entriesTemp) {
                                t.type = Token.XML_ANNOTATION;
                            }
                        } else if (token.content.toString().equals(stack.peekLast())) {
                            stack.removeLast();
                            token.type = Token.ELEMENT_END;
                        } else {
                            stack.offer(token.content.toString());
                        }
                    } else {
                        token.type = Token.ELEMENT;
                    }
                    temp.add(token);
                    token = null;
                    if (!entriesTemp.isEmpty()) {
                        temp.addAll(entriesTemp);
                        entriesTemp.clear();
                    }
                } else {
                    if (token != null) {
                        token.content.append(c);
                    } else {
                        throw new IllegalXMLFormatException();
                    }
                }
            } else if (c == '<') {
                if (token != null) {
                    if (!token.content.toString().matches("[\\t\\n\\s]*")) {
                        temp.add(token);
                    }
                }
                indexing = true;
                if (i + 1 >= len) {
                    throw new IllegalXMLFormatException();
                }
                ++i;
                token = new Token(Token.ELEMENT_START, "");
                if (charArray[i] == '/') {
                    ++i;
                    token.type = Token.ELEMENT_END;
                } else if (charArray[i] == '?') {
                    ++i;
                    token.type = Token.XML_ANNOTATION;
                }
                int pos = i;
                boolean indexString = false;
                Token entryTemp = null;
                while (true) {
                    if (pos >= len || charArray[pos] == '>') {
                        --pos;
                        i = pos;
                        break;
                    }
                    final char t = charArray[pos];
                    if (t == ' ') {
                        if (pos + 1 < len && charArray[pos + 1] == ' ') {
                            ++pos;
                            continue;
                        }
                        if (entryTemp != null) {
                            if (indexString) {
                                ++pos;
                                entryTemp.content.append(' ');
                                continue;
                            }
                            entriesTemp.add(entryTemp);
                        }
                        entryTemp = new Token(Token.XML_OBJECT, "");
                    } else {
                        if (t == '\n') {
                            ++pos;
                            continue;
                        }
                        if (entryTemp == null) {
                            token.content.append(t);
                        } else {
                            if (t == '"') {
                                indexString = !indexString;
                            }
                            entryTemp.content.append(t);
                        }
                    }
                    ++pos;
                }
                if (entryTemp != null) {
                    entriesTemp.add(entryTemp);
                }
            } else {
                if (token == null) {
                    if (c == '\t' || c == '\n' || c == ' ') {
                        continue;
                    }
                    token = new Token(Token.XML_OBJECT, "");
                }
                token.content.append(c);
            }
        }
        if (!stack.isEmpty()) {
            System.err.println(stack);
            throw new IllegalXMLFormatException();
        }
        this.tokens.addAll(temp);
    }

    public List<Token> getTokens () {
        return tokens;
    }

    public static void main (String[] args) throws IllegalXMLEscapeCharException, IllegalXMLFormatException, IOException {
        final Tokenizer tokenizer = Tokenizer.getInstance(new File("./test.xml"));
        tokenizer.read();
        tokenizer.tokenize();
        System.out.println(tokenizer.getTokens());
    }
}
