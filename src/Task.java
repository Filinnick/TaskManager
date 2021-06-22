import java.util.Date;

public class Task {

    private String name;
    private String body;
    private Date dateOfAlarm;
    private String contacts;
    private int id;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public Date getDateOfAlarm() {
        return dateOfAlarm;
    }

    public void setDateOfAlarm(Date dateOfAlarm) {
        this.dateOfAlarm = dateOfAlarm;
    }

    public String getContacts() {
        return contacts;
    }

    public void setContacts(String contacts) {
        this.contacts = contacts;
    }

    public int getId() { return id; }

    public void setId(int id) { this.id = id; }
}
