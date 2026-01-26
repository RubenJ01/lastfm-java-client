package io.github.rubeneekhof.lastfm.domain.model.user;

import java.util.List;

public record FriendsResult(
        String user,
        int page,
        int total,
        int perPage,
        int totalPages,
        List<Friend> friends) {

    public record Friend(
            String name,
            String realname,
            String url,
            String country,
            int playlists,
            long playcount,
            boolean subscriber,
            String type,
            long registered,
            int bootstrap,
            List<Image> images) {

        public record Image(String size, String url) {
            @Override
            public String toString() {
                return "Image { size: '" + size + "', url: '" + url + "' }";
            }
        }

        @Override
        public String toString() {
            StringBuilder sb = new StringBuilder();
            sb.append("Friend {\n");
            sb.append("    name: '").append(name).append("',\n");
            if (realname != null) {
                sb.append("    realname: '").append(realname).append("',\n");
            }
            sb.append("    url: '").append(url).append("',\n");
            if (country != null) {
                sb.append("    country: '").append(country).append("',\n");
            }
            sb.append("    playlists: ").append(playlists).append(",\n");
            sb.append("    playcount: ").append(playcount).append(",\n");
            sb.append("    subscriber: ").append(subscriber).append(",\n");
            sb.append("    type: '").append(type).append("',\n");
            sb.append("    registered: ").append(registered).append(",\n");
            sb.append("    bootstrap: ").append(bootstrap).append(",\n");
            sb.append("    images: [\n");

            if (images != null) {
                for (Image img : images) {
                    sb.append("        ").append(img).append(",\n");
                }
            }

            sb.append("    ]\n");
            sb.append("}");
            return sb.toString();
        }
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("FriendsResult {\n");
        sb.append("    user: '").append(user).append("',\n");
        sb.append("    page: ").append(page).append(",\n");
        sb.append("    total: ").append(total).append(",\n");
        sb.append("    perPage: ").append(perPage).append(",\n");
        sb.append("    totalPages: ").append(totalPages).append(",\n");
        sb.append("    friends: [\n");

        if (friends != null) {
            for (Friend friend : friends) {
                sb.append(friend.toString().indent(8).stripTrailing())
                        .append(",\n");
            }
        }

        sb.append("    ]\n");
        sb.append("}");
        return sb.toString();
    }
}
