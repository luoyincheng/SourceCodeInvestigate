package yincheng.sourcecodeinvestigate.library.markdownview.ext.twitter;

import com.vladsch.flexmark.Extension;
import com.vladsch.flexmark.html.HtmlRenderer;
import com.vladsch.flexmark.parser.Parser;
import com.vladsch.flexmark.util.options.MutableDataHolder;

import yincheng.sourcecodeinvestigate.library.markdownview.ext.twitter.internal.TwitterNodePostProcessor;
import yincheng.sourcecodeinvestigate.library.markdownview.ext.twitter.internal.TwitterNodeRenderer;


public class TwitterExtension implements Parser.ParserExtension, HtmlRenderer.HtmlRendererExtension {
    private TwitterExtension() {
    }

    public static Extension create() {
        return new TwitterExtension();
    }

    @Override
    public void extend(Parser.Builder parserBuilder) {
        parserBuilder.postProcessorFactory(new TwitterNodePostProcessor.Factory(parserBuilder));
    }

    @Override
    public void rendererOptions(final MutableDataHolder options) {

    }

    @Override
    public void parserOptions(final MutableDataHolder options) {

    }

    @Override
    public void extend(HtmlRenderer.Builder rendererBuilder, String rendererType) {
        if (rendererType.equals("HTML")) {
            rendererBuilder.nodeRendererFactory(new TwitterNodeRenderer.Factory());
        }
    }
}
