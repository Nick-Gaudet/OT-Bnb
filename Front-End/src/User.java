public class User {

    private boolean canRent, canPost; // Full standard (FS) , Rent Standard (RS) , Post Standard (PS)
    private String userName;
    private String privileges;

    public User(String userName, String privileges){

        this.userName = userName;
        this.privileges = privileges;

        if (privileges.equals("FS")){
            canRent = true;
            canPost = true;
        }
        else if (privileges.equals("RS")){
            canPost = false;
            canRent = true;
        }
        else if (privileges.equals("PS")){
            canPost = true;
            canRent = false;
        }

    }

    public boolean canRent() {
        return canRent;
    }

    public void setCanRent(boolean canRent) {
        this.canRent = canRent;
    }

    public boolean canPost() {
        return canPost;
    }

    public void setCanPost(boolean canPost) {
        this.canPost = canPost;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPrivileges() {
        return privileges;
    }

    public void setPrivileges(String privileges) {
        this.privileges = privileges;
    }

    public String toString(){
        return userName + "|" + privileges ;
    }
}
