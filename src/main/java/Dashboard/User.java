package Dashboard;

public class User {

    private String givenName, familyName, gmail, gmailOld, password,confirmPass, authCode;
    private boolean sent;

//    User(String firstName, String lastName, String gmail, String gmailOld, String password, String authCode,
//         boolean sent ){
//        this.givenName = firstName;
//        this.familyName = lastName;
//        this.gmail = gmail;
//        this.gmailOld = gmailOld;
//        this.password = password;
//        this.authCode = authCode;
//        this.sent = sent;
//    }
//
//    User(String firstName, String lastName, String gmail, String gmailOld, boolean sent){
//        this.givenName = firstName;
//        this.familyName = lastName;
//        this.gmail = gmail;
//        this.gmailOld = gmailOld;
//        this.sent = sent;
//    }

    public User(){}

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

    public String getGmailOld() {
        return gmailOld;
    }

    public void setGmailOld(String gmailOld) {
        this.gmailOld = gmailOld;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getConfirmPass() {

        return confirmPass;
    }

    public void setConfirmPass(String confirmPass) {
        this.confirmPass = confirmPass;
    }

    public String getAuthCode() {
        return authCode;
    }

    public void setAuthCode(String authCode) {
        this.authCode = authCode;
    }

    public boolean isSent() {
        return sent;
    }

    public void setSent(boolean sent) {
        this.sent = sent;
    }
}
