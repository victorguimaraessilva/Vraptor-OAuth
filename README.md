# Vraptor-OAuth
OAuth simples via interceptor Vraptor 4

Este tutorial irá mostrar uma forma simples e eficiênte de implementar o OAuth2 no FrameWork Vraptor4 através do Interceptor e que poderá ajudar vários outros desenvolvedores a implementar o protoloco.

Quando me deparei com a RFC do OAuth2 levei bastante tempo para analisar todas as formas de implementação que podemos realizar através de suas especificações. Neste post irei implementar uma forma fácil de OAuth no Vraptor utilizando também MySQL para guardar todos os tokens de acesso para cada cliente. Existem diversas formas de se implementar um OAuth2, caso essa forma proposta neste tutorial não seja ideal para seu contexto, as outras várias formas de implementação poderão ser encontradas no site: https://oauth.net/2/


Registrando seu Usuário:

Antes de começar a implementar o OAuth, você deverá primeiro criar um cadastro(register) de usuários em seu sistema e também uma página de login para que o usuário possa autenticar em seu sistema.

Assim que o usuário cadastrar, ele irá receber um Client_ID que irá identificá-lo no processo de autenticação.

1º Passo do OAuth2.0:

O primeiro passo do OAuth será obter uma autorização do usuário para que o mesmo envie suas credenciais e consiga consequentemente autenticar no sistema. Existem diversas formas para a obtenção da autorização, neste tutorial irei implementar uma forma bastante simples de obter a autorização seguida do código de acesso a ser utilizado pelo usuário para obter acesso aos recursos da API.

Forma de Obtenção do Cliente_ID:

1.1) O usuário irá fornecer seu username ou email juntamente com sua senha.

1.2) O servidor irá verificar se o usuário e senha está correto.

1.2.1) Caso seja um cliente inválido o servidor irá responder como access_denied.

1.2.2) Caso seja um cliente válido o servidor irá enviar o Client_ID e deverá criar as seguintes variáveis:

access_token: Código de acesso criado pelo servidor (MD5, SHA, BASE64) que será enviado ao cliente.
refresh_tolen: Código utilizado para atualizar o access_token caso este expire ou seja perdido pelo cliente.
expires_at: Irá guardar o tempo de expiração do token atual.

2ºPasso do OAuth2.0:

2.1) Assim que o usuário receber o token de acesso, o token de atualização e a data de validade deste token de acesso, o mesmo
deverá utilizar esse access_token em todas as suas requisições à API para que a mesma autorize seu acesso às informações.

2.2) Como no Vraptor temos a opção de criar um Interceptor que irá interceptar todas as requisições, podemos utilizá-lo para capturar o access token enviado pelo usuário, juntamente com o client_id e verificar se estão corretos. 

2.2.1) Caso o access_token + client_id estejam corretos o Interceptor deverá permitir a conexão ao controller (API)

2.2.2) Caso esteja errado, o usuário será informado e seu accesso será bloqueado redirecionando para a página de login.

2.2.3) Caso o access_token + client_id esteja correto porém expirado, o usuário será informado que seu token foi expirado, obrigando o mesmo a enviar o access_token + client_id juntamente com o refresh_token.

2.2.3.1) Caso os dados estejam corretos, um novo access_token e refresh_token serão enviados ao cliente assim como na sua primeira autenticação autorizando o acesso do mesmo à API com seu novo access_token.

2.2.3.2)  Caso os dados estejam incorretos, o usuário será informado e seu accesso será bloqueado redirecionando para a página de login.


Obs: Devemos lembrar que o RegisterController e o LoginController deverá sempre permitir o acesso do usuário
sem necessidade do access_token pois será através destes que o usuário poderá obter seu acess_token.

----------------------------------------------------------------------------------------------------------------------------------------
Tecnologias e Dependências Utilizadas:

VRaptor 4 

Maven 5

Java 8
Mysql 5 
Java8 plugin
Simple mail plugin
Servlet Container
JPA plugin
----------------------------------------------------------------------------------------------------------------------------------------

Caso possua dicas ou dúvidas poderá me contactar através do email:

victorgs301194@hotmail.com 
