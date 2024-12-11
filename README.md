# Controle-de-Artes
Sistema Desktop Java: Controle de artes para Gráfica rápida / JavaFX e JDBC
<br>Status: Versão 1.0.5 <br>
<img src="https://raw.githubusercontent.com/juliansempre/Controle-de-Artes/main/img/imagem.jpg"></img>
<br>
=======================
Instalação:
=======================

Servidor:
Instala o mysql server
Cria a senha de root = toor
Retire a senha de root:
ALTER USER 'root'@'localhost' IDENTIFIED BY '';
Baixe o arquivo *.sql da pasta library e insira do mysql:
source C:\Users\Meu Computador\Desktop\controledeartes.sql

///////////////////////////////////////////
//////     MYSQL EM REDE LOCAL   //////////
///////////////////////////////////////////
[my.cnf]
bind-address = 0.0.0.0

CREATE USER 'root'@'192.168.0.206' IDENTIFIED BY '';
GRANT ALL PRIVILEGES ON controledeartes.* TO 'root'@'192.168.0.206';
FLUSH PRIVILEGES;
