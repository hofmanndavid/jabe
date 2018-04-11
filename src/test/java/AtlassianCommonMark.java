import org.commonmark.node.*;
import org.commonmark.parser.Parser;
import org.commonmark.renderer.NodeRenderer;
import org.commonmark.renderer.html.*;

import java.util.Collections;
import java.util.Map;
import java.util.Set;

public class AtlassianCommonMark {
    public static void main( String[] args ) {
        simple();
        addOrChangeAttrsOfHtmlElements();
        paseNodesVisitor();
        customHtmlRendering();
    }

    static void simple() {
        Parser parser = Parser.builder().build();
        Node document = parser.parse("This is *Sparta*");
        HtmlRenderer renderer = HtmlRenderer.builder().build();
        String rendered = renderer.render(document);// "<p>This is <em>Sparta</em></p>\n"

        System.out.println(rendered);
    }

    static void addOrChangeAttrsOfHtmlElements() {
        Parser parser = Parser.builder().build();
        HtmlRenderer renderer = HtmlRenderer.builder()
                .attributeProviderFactory(new AttributeProviderFactory() {
                    public AttributeProvider create(AttributeProviderContext context) {
                        return new ImageAttributeProvider();
                    }
                })
                .build();

        Node document = parser.parse("![text](/url.png)");
        String rendered = renderer.render(document);

        System.out.println(rendered);
// "<p><img src=\"/url.png\" alt=\"text\" class=\"border\" /></p>\n"


    }

    static void paseNodesVisitor() {
        Parser parser = Parser.builder().build();
        Node node = parser.parse("Example\n=======\n\nSome more text");
        WordCountVisitor visitor = new WordCountVisitor();
        node.accept(visitor);
        HtmlRenderer renderer = HtmlRenderer.builder().build();

        String rendered = renderer.render(node);
        System.out.println(
                String.format("%d words rendered: \n%s",
                        visitor.wordCount,
                        rendered));

    }

    static void customHtmlRendering() {
        Parser parser = Parser.builder().build();

        HtmlRenderer renderer = HtmlRenderer.builder()
                .nodeRendererFactory(new HtmlNodeRendererFactory() {
                    public NodeRenderer create(HtmlNodeRendererContext context) {
                        return new IndentedCodeBlockNodeRenderer(context);
                    }
                })
                .build();
//        renderer = HtmlRenderer.builder().build(); // test without the renderer factory...
        Node document = parser.parse("Example:\n\n    code");
        String rendered = renderer.render(document);
// "<p>Example:</p>\n<pre>code\n</pre>\n"

        System.out.println(rendered);

    }

    static class IndentedCodeBlockNodeRenderer implements NodeRenderer {
        private final HtmlWriter html;

        IndentedCodeBlockNodeRenderer(HtmlNodeRendererContext context) {
            this.html = context.getWriter();
        }
        public Set<Class<? extends Node>> getNodeTypes() {
            // Return the node types we want to use this renderer for.
            return Collections.<Class<? extends Node>>singleton(IndentedCodeBlock.class);
        }
        public void render(Node node) {
            // We only handle one type as per getNodeTypes, so we can just cast it here.
            IndentedCodeBlock codeBlock = (IndentedCodeBlock) node;
            html.line();
            html.tag("pre");
            html.text(codeBlock.getLiteral());
            html.tag("/pre");
            html.line();
        }
    }

    static class ImageAttributeProvider implements AttributeProvider {
        public void setAttributes(Node node, String tagName, Map<String, String> attributes) {
            if (node instanceof Image) {
                attributes.put("class", "border");
            }
        }
    }
    static class WordCountVisitor extends AbstractVisitor {
        public int wordCount = 0;

        public void visit(Text text) {
            // This is called for all Text nodes. Override other visit methods for other node types.

            // Count words (this is just an example, don't actually do it this way for various reasons).
            wordCount += text.getLiteral().split("\\W+").length;

            // Descend into children (could be omitted in this case because Text nodes don't have children).
            visitChildren(text);
        }
    }
}
