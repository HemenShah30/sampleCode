"""
Hemen Shah
hemenshah30@gatech.edu

I worked on this hw alone using only the resources provided in class

I wanted to make a game.
I tried several things and this seemed to be the simplest to execute
in the graphics module(the documentation for it sucks...)

I remembered a pyscadelic trip of a program that I once saw which
made lots of colorful balls on the screens and left a trail but i saw
that it would not work well in python because it would redraw each
object every time making it laggy as time goes on. I tried to install
pygame but that was a little frustrating as i tried to use idle for
it. I also looked into making an rpg but other than using classes I
had little idea how to make it work.

I made a text game feature where I had user input on wether they
wanted to play a game and then giving a choice of which game to play
but i couldnt think of many games viable for a small text input and
output so that avenue was closed. Eventually I came back to graphics
module and i ended up figuring it out.


"""

from Graphics import *
from random import random
from Myro import wait
from math import sqrt

#makes a window
win = Window("Dodge the ball",400,400)
win.mode = "manual"

#makes the balls that the user is supposed to dodge
balls = []
dx = []
dy = []
for x in range(100):
    ball = Circle((random()*100,random()*100),3)
    ball.color = Color(random()*255,random()*255,random()*255)
    balls.append(ball)
    dx.append(random()*6-3)
    dy.append(random()*6-3)
for ball in balls:
    ball.draw(win)
win.update()

#writes out instructions
instructions1 = Text((200,140),"Dodge the other balls")
instructions2 = Text((200,180),"Stay alive as long as you can")
instructions1.draw(win)
instructions2.draw(win)

#makes the player
player = Circle((200,200),5)
player.draw(win)

#moves the player
def playerMove(obj, event):
    player.moveTo(event.x,event.y)

#checks if player loses and then ends game and script if met
def lose(balls,player):
    playerX = player.getX()
    playerY = player.getY()
    losecondition = False
    for ball in balls:
        ballX = ball.getX()
        ballY = ball.getY()
        dist = sqrt((ballX - playerX)**2+(ballY-playerY)**2)
        if dist < (3+5):
            losecondition = True
    if losecondition == True:
        global score
        win.clear()
        if score>40:
            congrats = Text((200,140),"Congratulations!!!")
            congrats.draw(win)
        playerScore = Text((200,160),"Your score is: {}. Run again to play again".format(score))
        playerScore.draw(win)
        #allows to end game/script
        global playing
        playing = False




win.update()
#score: just a random scoring mechanism
score = 0.0
playing = True

#main running code
while playing:
    for i in range(len(balls)):
        ball = balls[i]
        ball.move(dx[i],dy[i])
        if ball.x > 400 or ball.x <0:
            dx[i] = -dx[i]
        if ball.y > 400 or ball.y <0:
            dy[i] = -dy[i]
    score = score +0.2
    if score >20:
        instructions1.undraw()
        instructions2.undraw()
    wait(.1)
    onMouseMovement(playerMove)
    lose(balls,player)
    win.update()