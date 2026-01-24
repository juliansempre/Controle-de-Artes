# Controle-de-Artes
Sistema Desktop Java: Controle de artes para Gráfica rápida / JavaFX e JDBC
<br>Status: Versão 1.0.5 <br>
<img src="https://raw.githubusercontent.com/juliansempre/Controle-de-Artes/main/img/imagem.jpg"></img>
<br>
<pre>
=======================
Instalação:
=======================

Servidor:
Instale o mysql server
Cria a senha de root = toor
<sub>Atenção: Originalmente o mysql do projeto não possui senha ou seja root com "senha em branco".
Devido a atualizações do mysql a senha root deverá ser trocada no ConectaDB.java java por "toor".</sub>
<strike>
Retire a senha de root:
ALTER USER 'root'@'localhost' IDENTIFIED BY '';
</strike>
Baixe o arquivo *.sql da pasta library e insira do mysql:
[cmd]->(mysql) source C:\Users\Meu Computador\Desktop\controledeartes.sql

///////////////////////////////////////////
//////     MYSQL EM REDE LOCAL   //////////
///////////////////////////////////////////
Mostrar o datadir: 
select @@datadir;
C:\ProgramData\MySQL\MySQL Server 8.0\my.ini
[my.cnf] ou [my.ini]
bind-address = 0.0.0.0

CREATE USER 'root'@'192.168.0.206' IDENTIFIED BY '';
GRANT ALL PRIVILEGES ON controledeartes.* TO 'root'@'192.168.0.206';
FLUSH PRIVILEGES;
-------------------------------------------
adicionar um usuario permitido:

CREATE USER 'root'@'192.168.0.205' IDENTIFIED BY '';
GRANT ALL PRIVILEGES ON controledeartes.* TO 'root'@'192.168.0.205';
---------------------------------------------
Desativar segurança:
[my.ini]

skip-grant-tables

</pre>
