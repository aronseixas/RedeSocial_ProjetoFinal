public class InvalidPasswordException extends RuntimeException{
    public String getMessageIPE(){
        return "\nSenha incorreta. \nNão é possível realizar a operação. \nDigite a senha correta. \nRetornando ao Menu Inicial\n";
    }
}
