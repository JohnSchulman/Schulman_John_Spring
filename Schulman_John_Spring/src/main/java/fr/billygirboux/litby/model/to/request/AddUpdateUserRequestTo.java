package fr.billygirboux.litby.model.to.request;

import fr.billygirboux.litby.model.to.UserTo;
import javax.validation.constraints.NotEmpty;


public class AddUpdateUserRequestTo extends UserTo {

    // Tu n'expose pas ton mot de passe mais
    @NotEmpty(message = "Username mandatory")
    private String password;

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

}
