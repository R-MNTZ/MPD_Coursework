package coursework.gcu.mpdcoursework;
//Ramon Martinez Fernandez S1631216
public class CurrentIncidents {

    private String title;
    private String description;
    private String link;
    private String geo;
    private String author;
    private String comments;
    private String pubDate;

    public CurrentIncidents(){
        title = "";
        description = "";
        link = "";
        geo = "";
        author = "";
        comments = "";
        pubDate = "";
    }

    public CurrentIncidents(String atitle, String adescription, String alink, String ageo, String aauthor,
                            String acomments, String apubDate){
        title = atitle;
        description = adescription;
        link = alink;
        geo = ageo;
        author = aauthor;
        comments = acomments;
        pubDate = apubDate;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String atitle) {
        title = atitle;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String adescription) {
        adescription = adescription.replace("<br />", "\n");
        description = adescription;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String alink) {
        link = alink;
    }

    public String getGeo() {
        return geo;
    }

    public void setGeo(String ageo) {
        geo = ageo;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthore(String aauthor) {
        if (aauthor == ""){
            aauthor = "N/A";
        }
        author = aauthor;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String acomments) {
        if (acomments == ""){
            acomments = "N/A";
        }
        comments = acomments;
    }

    public String getPubDate() {
        return pubDate;
    }

    public void setPubDate(String apubDate) {
        String day, mon, year, time;
        day = apubDate.substring(5,7);
        year = apubDate.substring(12,16);
        time = apubDate.substring(17,25);
        mon = "";
        switch (apubDate.substring(8,11))
        {
            case "Jan":
                mon = "01";
                break;
            case "Feb":
                mon = "02";
                break;

            case "Mar":
                mon = "03";
                break;

            case "Apr":
                mon = "04";
                break;

            case "May":
                mon = "05";
                break;

            case "Jun":
                mon = "06";
                break;

            case "Jul":
                mon = "07";
                break;

            case "Aug":
                mon = "08";
                break;

            case "Sep":
                mon = "09";
                break;

            case "Oct":
                mon = "10";
                break;

            case "Nov":
                mon = "11";
                break;

            case "Dec":
                mon = "12";
                break;

        }
        pubDate = day + "/" + mon + "/" + year + " " + time;
    }

    public String[] getItem() {
       String[] items = {title, description, link, geo, comments, author, pubDate};
       return items;
    }

}
