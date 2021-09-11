package Dashboard;

public class User {

    private String givenName, familyName, gmail, password, authCode;

    User(String firstName, String lastName, String gmail, String password, String authCode ){
        this.givenName = firstName;
        this.familyName = lastName;
        this.gmail = gmail;
        this.password = password;
        this.authCode = authCode;
    }

    User(String firstName, String lastName, String gmail){
        this.givenName = firstName;
        this.familyName = lastName;
        this.gmail = gmail;
    }

    User(){}

    public String getGivenName() {
        return givenName;
    }

    public void setGivenName(String givenName) {
        this.givenName = givenName;
    }

    public String getFamilyName() {
        return familyName;
    }

    public void setFamilyName(String familyName) {
        this.familyName = familyName;
    }

    public String getGmail() {
        return gmail;
    }

    public void setGmail(String gmail) {
        this.gmail = gmail;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getAuthCode() {
        return authCode;
    }

    public void setAuthCode(String authCode) {
        this.authCode = authCode;
    }
}
