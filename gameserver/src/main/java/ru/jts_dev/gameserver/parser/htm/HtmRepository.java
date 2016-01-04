package ru.jts_dev.gameserver.parser.htm;

import com.googlecode.htmlcompressor.compressor.HtmlCompressor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;
import ru.jts_dev.gameserver.model.Language;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * @author Java-man
 * @since 04.01.2016
 */
@Component
public class HtmRepository {
    private static final HtmlCompressor HTML_COMPRESSOR = createHtmlCompressor();

    private static final Logger LOGGER = LoggerFactory.getLogger(HtmRepository.class);

    private final HtmRepositoryConfig config;

    @Autowired
    public HtmRepository(HtmRepositoryConfig config) {
        this.config = config;
    }

    @PostConstruct
    private void loadHtm() {
        if (config.getHtmRepositoryType() != HtmRepositoryType.ENABLE)
            return;

        for (Language language : Language.values()) {
            Path htmPath = Paths.get("htm-" + language.getShortName());
            try {
                Files.walk(htmPath).forEach(path -> getHtm(path.toString()));
            } catch (IOException e) {
                throw new IllegalArgumentException(e);
            }
        }
    }

    public String getHtm(String htmPath) {
        if (config.getHtmRepositoryType() == HtmRepositoryType.DISABLE)
            return readHtm(htmPath);
        else
            return getCachedHtm(htmPath);
    }

    @Cacheable("htm")
    private String getCachedHtm(String htmPath) {
        String htm = readHtm(htmPath);
        htm = compressHtm(HTML_COMPRESSOR, htm);
        return htm;
    }

    private String readHtm(String htmPath) {
        try {
            Path path = Paths.get(ClassLoader.getSystemResource(htmPath).toURI());

            byte[] content = Files.readAllBytes(path);
            String htm = new String(content, StandardCharsets.UTF_8);
            return htm;
        } catch (IOException | URISyntaxException e) {
            throw new IllegalArgumentException(e);
        }
    }

    private static String compressHtm(HtmlCompressor compressor, String content) {
        String result = compressor.compress(content);

        LOGGER.info(String.format(
                "Compression time: %,d ms, Original size: %,d bytes, Compressed size: %,d bytes",
                compressor.getStatistics().getTime(),
                compressor.getStatistics().getOriginalMetrics().getFilesize(),
                compressor.getStatistics().getCompressedMetrics().getFilesize()
        ));

        return result;
    }

    private static HtmlCompressor createHtmlCompressor() {
        HtmlCompressor htmlCompressor = new HtmlCompressor();

        htmlCompressor.setEnabled(true);                   //if false all compression is off (default is true)
        htmlCompressor.setRemoveComments(true);            //if false keeps HTML comments (default is true)
        htmlCompressor.setRemoveMultiSpaces(true);         //if false keeps multiple whitespace characters (default is true)
        htmlCompressor.setRemoveIntertagSpaces(true);      //removes iter-tag whitespace characters
        htmlCompressor.setRemoveQuotes(true);              //removes unnecessary tag attribute quotes
        htmlCompressor.setSimpleDoctype(false);             //simplify existing doctype
        htmlCompressor.setRemoveScriptAttributes(false);    //remove optional attributes from script tags
        htmlCompressor.setRemoveStyleAttributes(false);     //remove optional attributes from style tags
        htmlCompressor.setRemoveLinkAttributes(true);      //remove optional attributes from link tags
        htmlCompressor.setRemoveFormAttributes(true);      //remove optional attributes from form tags
        htmlCompressor.setRemoveInputAttributes(true);     //remove optional attributes from input tags
        htmlCompressor.setSimpleBooleanAttributes(true);   //remove values from boolean tag attributes
        htmlCompressor.setRemoveJavaScriptProtocol(false);  //remove "javascript:" from inline event handlers
        htmlCompressor.setRemoveHttpProtocol(false);        //replace "http://" with "//" inside tag attributes
        htmlCompressor.setRemoveHttpsProtocol(false);       //replace "https://" with "//" inside tag attributes
        htmlCompressor.setPreserveLineBreaks(false);        //preserves original line breaks
        htmlCompressor.setRemoveSurroundingSpaces("all"); //remove spaces around provided tags

        htmlCompressor.setCompressCss(false);               //compress inline css
        htmlCompressor.setCompressJavaScript(false);        //compress inline javascript

        htmlCompressor.setGenerateStatistics(true);

        return htmlCompressor;
    }
}
