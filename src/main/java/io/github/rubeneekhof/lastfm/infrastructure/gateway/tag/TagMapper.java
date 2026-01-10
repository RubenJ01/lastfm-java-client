package io.github.rubeneekhof.lastfm.infrastructure.gateway.tag;

import io.github.rubeneekhof.lastfm.domain.model.Tag;
import io.github.rubeneekhof.lastfm.infrastructure.gateway.tag.response.GetInfoResponse;

public class TagMapper {

    public static Tag from(GetInfoResponse response) {
        if (response == null || response.tag == null) {
            return null;
        }

        GetInfoResponse.TagData data = response.tag;
        return new Tag(
                data.name,
                data.url,
                parseNumber(data.reach),
                parseNumber(data.total),
                mapWiki(data.wiki)
        );
    }

    private static int parseNumber(Object value) {
        if (value == null) {
            return 0;
        }
        
        if (value instanceof Number) {
            return ((Number) value).intValue();
        }
        
        if (value instanceof String) {
            String str = (String) value;
            if (str.isBlank()) {
                return 0;
            }
            try {
                return Integer.parseInt(str);
            } catch (NumberFormatException e) {
                try {
                    return (int) Double.parseDouble(str);
                } catch (NumberFormatException ex) {
                    return 0;
                }
            }
        }
        
        return 0;
    }

    private static Tag.Wiki mapWiki(GetInfoResponse.Wiki wiki) {
        if (wiki == null) {
            return null;
        }
        return new Tag.Wiki(wiki.published, wiki.summary, wiki.content);
    }
}
