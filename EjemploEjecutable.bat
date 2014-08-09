@echo off
title Game Server Console
:start
echo Starting L2J Game Server.
echo.
REM -------------------------------------
REM Default parameters for a basic server.
java -Djava.util.logging.manager=com.l2jserver.util.L2LogManager -Djavax.net.ssl.keyStore=mySrvKeystore -Djavax.net.ssl.keyStorePassword=123456 -Xms1024m -Xmx1024m -cp L2jSec.jar;./../libs/*;l2jserver.jar com.l2jopenguard.L2jSecServer com.l2jserver.gameserver.GameServer
REM
REM If you have a big server and lots of memory, you could experiment for example with
REM java -server -Xmx1536m -Xms1024m -Xmn512m -XX:PermSize=256m -XX:SurvivorRatio=8 -Xnoclassgc -XX:+AggressiveOpts
REM -------------------------------------
if ERRORLEVEL 2 goto restart
if ERRORLEVEL 1 goto error
goto end
:restart
echo.
echo Admin Restart ...
echo.
goto start
:error
echo.
echo Server terminated abnormaly
echo.
:end
echo.
echo server terminated
echo.
pause
