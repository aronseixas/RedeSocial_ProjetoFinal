import java.util.Scanner;

public class RedeSocial {
    static Scanner entrada = new Scanner(System.in);
    static Usuario[] listaDeUsuarios = new Usuario[100];
    static String opcaoMenuPrincipal;
    static String opcaoPerfilUsuario;
    static int quantUsuarios = 0;
    static String loginUsuario;
    static String senhaUsuario;
    static int countUsuario =0, posicaoDoUsuario = 0;

    public static void main(String[] args) {
        MenuInicial();
        do{
            opcaoMenuPrincipal = TelaInicial();

            switch (opcaoMenuPrincipal) {
                case "C":
                    try{
                        cadastroDeUsuario();
                    }catch (CampoVazioException e){
                        System.out.print(e.getMessageCVE());
                    } catch (LoginExistenteExcepetion e){
                        System.out.print(e.getMessageLEE());
                    }
                    break;
                case "E":
                    try{
                        loginDeUsuario();
                    }catch (NoUserPlataform e){
                        System.out.print(e.getMessageNUP());
                    } catch (UserNotFoundException e){
                        System.out.print(e.getMessageUNFE());
                    } catch (InvalidPasswordException e){
                        System.out.print(e.getMessageIPE());
                    } catch (CampoVazioException e){
                        System.out.print(e.getMessageCVE());
                    }
                    break;
                case "F":
                    System.out.println("\nVocê solicitou a saída da Rede #Dev_Makers. \nEstamos ansiosos para uma próxima visita.");
                    break;
                case "L":
                    try{
                        ListarUsuarios();
                    }catch (SemUsuariosCadastradosException e){
                        System.out.println(e.getMessageSUCE());
                    }
                    break;
                default:
                    System.out.println("\nFoi digitado um código inválido. Retornando para o Menu principal.");
                    break;
            }
            System.out.println("------------------------------------------------------------------------------------------------------");
        }while (!opcaoMenuPrincipal.equals("F"));
    }
    static void MenuInicial(){
        System.out.println("------------------------------------------------------------------------------------------------------");
        System.out.println("Seja Bem-vindo a rede social #Dev_Makers.");
        System.out.println("------------------------------------------------------------------------------------------------------");
    }
    static String TelaInicial(){
        System.out.println("MENU INICIAL");
        System.out.println("Neste momento voce está na página inical. Digite o código referente a ação que desejas fazer.");
        System.out.println("'C': Cadastro");
        System.out.println("'E': Entrar");
        System.out.println("'L': Listar Usuários");
        System.out.println("'F': Fechar");
        System.out.print("Opção desejada: ");
        return entrada.nextLine().toUpperCase();
    }
    static void ListarUsuarios() throws SemUsuariosCadastradosException{
        System.out.println("\nSolicitação de listagem de usuários na Rede Social #Dev#Makers");
        if(quantUsuarios == 0){
            throw new SemUsuariosCadastradosException();
        } else {
            for (int i = 0; i < quantUsuarios; i++) {
                System.out.printf("Usuário %d - Nome: %s login: %s\n",i+1,listaDeUsuarios[i].Nome, listaDeUsuarios[i].Login);
            }
            System.out.println("\nListagem de usuários cadastrados completa");
        }
    }
    static void cadastroDeUsuario() throws CampoVazioException, LoginExistenteExcepetion{
        Usuario users = new Usuario();
        System.out.print("\nSolicitação de cadastro de novo usuário solicitada. \nPara isso é necessário inserir algumas informações. \nDigite o nome desejado: ");
        users.Nome = entrada.nextLine();
        System.out.print("Digite o Login: ");
        users.Login = entrada.nextLine();
        System.out.print("Digite a senha: ");
        users.Senha = entrada.nextLine();
        System.out.println("\nValidando infomações inseridas ...");

        if (users.Nome.isEmpty() || users.Senha.isEmpty() || users.Login.isEmpty()){
            throw new CampoVazioException();
        }

        for(int i=0; i<quantUsuarios;i++) {
            if (users.Login.equals(listaDeUsuarios[i].Login)) {
                throw new LoginExistenteExcepetion();
            }
        }

        listaDeUsuarios[quantUsuarios] = users;
        System.out.printf("\nAção realizada com sucesso. \nSeja bem-vindo %s, seu cadastro foi inserido com sucesso e seu login a partir de agora é: %s\n", listaDeUsuarios[quantUsuarios].Nome, listaDeUsuarios[quantUsuarios].Login);
        quantUsuarios++;
    }
    static void loginDeUsuario() throws CampoVazioException, NoUserPlataform, UserNotFoundException, InvalidPasswordException{
        countUsuario = 0;

        if(quantUsuarios == 0){
            throw new NoUserPlataform();
        }

        System.out.println("\nSolicitação de Login realizada. \nRedirecionando para Tela de Login de usuário");
        System.out.println("------------------------------------------------------------------------------------------------------");
        System.out.println("MENU DE ENTRADA NO PERFIL");
        System.out.println("Para entrar no seu perfil, insira suas informações ");
        System.out.print("DIGITE SEU LOGIN: ");
        loginUsuario = entrada.nextLine();
        System.out.print("DIGITE SUA SENHA: ");
        senhaUsuario = entrada.nextLine();
        System.out.println("\nValidando informações inseridas...");

        //Identificação de Campos vazios
        if (loginUsuario.isEmpty() || senhaUsuario.isEmpty()){
            throw new CampoVazioException();
        }

        //IDENTIFICAÇÃO DO USUARIO
        for (int i = 0; i < quantUsuarios; i++) {
            if (loginUsuario.equals(listaDeUsuarios[i].Login)){
                countUsuario ++;
                posicaoDoUsuario = i;
            }
        }
        if(countUsuario==1){
            System.out.print("\nLogin ✓");
        }else {
            throw new UserNotFoundException();
        }

        //IDENTIFICAÇÃO DA SENHA
        if(senhaUsuario.equals(listaDeUsuarios[posicaoDoUsuario].Senha)){
            System.out.println("\nSenha ✓");
        } else {
            throw new InvalidPasswordException();
        }

        if(loginUsuario.equals(listaDeUsuarios[posicaoDoUsuario].Login) && senhaUsuario.equals(listaDeUsuarios[posicaoDoUsuario].Senha)){
            do{
                opcaoPerfilUsuario = TelaDePerfil();

                switch (opcaoPerfilUsuario) {
                    case "P":
                        try{
                            Postar(posicaoDoUsuario);
                        } catch (CampoVazioException e){
                            System.out.print(e.getMessageCVE());
                        }
                        break;
                    case "T":
                        System.out.println("\nVocê solicitou a exibição da sua timeline.");
                        try{
                            ExibirTimeLine(listaDeUsuarios[posicaoDoUsuario].quantPosts, posicaoDoUsuario);
                        }catch (NoPostsOnPlataform e){
                            System.out.println(e.getMessageNPOP());
                        }
                        break;
                    case "S":
                        System.out.println("\nVocê Optou por sair do perfil. \nObrigado pela visita e estamos ansiosos pelo seu retorno.");
                        break;
                    default:
                        System.out.println("\nFoi Digitado um comando inválido. Insira um comando válido.");
                        break;
                }
            }while(!opcaoPerfilUsuario.equals("S"));
        }
    }
    static String TelaDePerfil(){
        System.out.println("------------------------------------------------------------------------------------------------------");
        System.out.printf("Bem-Vindo ao seu Perfil %s.",listaDeUsuarios[posicaoDoUsuario].Nome);
        System.out.println("\nO que você gostaria de fazer?");
        System.out.println("\nDigite o código referente ao seu desejo.");
        System.out.println("'P': Postar");
        System.out.println("'T': TimeLine");
        System.out.println("'S': Sair");
        System.out.print("Opção desejada: ");
        return entrada.nextLine().toUpperCase();
    }
    static void Postar (int posicaoUsuarioLogado) throws CampoVazioException{
        Post fazerPost = new Post();
        System.out.print("\nVocê solicitou a inserção de um post na sua timeline. \nPara isso, digite a data do post (dd/mm/aaaa): ");
        fazerPost.data = entrada.nextLine();
        System.out.print("Digite a hora do post (hh:mm): ");
        fazerPost.hora = entrada.nextLine();
        System.out.print("Digite o conteúdo do post: ");
        fazerPost.texto = entrada.nextLine();

        System.out.println("\nValidando informações inseridas...\n");

        if (fazerPost.data.isEmpty() || fazerPost.hora.isEmpty() || fazerPost.texto.isEmpty()){
            throw new CampoVazioException();
        }

        listaDeUsuarios[posicaoUsuarioLogado].listaDePosts[listaDeUsuarios[posicaoDoUsuario].quantPosts] = fazerPost;
        System.out.printf("Post %d inserido com sucesso\n",listaDeUsuarios[posicaoDoUsuario].quantPosts+1);
        listaDeUsuarios[posicaoDoUsuario].quantPosts++;
    }
    static void ExibirTimeLine(int postsFeitos, int posicaoUsuarioLogado) throws NoPostsOnPlataform{
        if(postsFeitos == 0){
            throw new NoPostsOnPlataform();
        }else{
            for (int i = 0; i < postsFeitos; i++) {
                System.out.printf("Post %d - ",i+1);
                System.out.printf("Dia: %s ", listaDeUsuarios[posicaoUsuarioLogado].listaDePosts[i].data);
                System.out.printf("às %s. ", listaDeUsuarios[posicaoUsuarioLogado].listaDePosts[i].hora);
                System.out.println(listaDeUsuarios[posicaoUsuarioLogado].listaDePosts[i].texto);
            }
        }
    }
}