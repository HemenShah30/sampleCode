"""
We worked on the homework assignment alone, using only this semester's 
course materials.

Hemen Shah
hemenshah30@gatech.edu

Joel Anderson
janderson97@mail.gatech.edu

Section A5
"""

## from Myro import *
## init()

def findYellow():
    while True:
        setPicSize("small")
        pic = takePicture()
        print("picture taken")
        pixel = 0
        for p in getPixels(pic):
            r = getRed(p)
            b = getBlue(p)
            g = getGreen(p)
            if r > b+10 and g > b+10 and r <g+18 and g<r+18 :
                pixel +=1
        yellow = pixel / (getHeight(pic)*getWidth(pic))
        if yellow >= .85:
            turnLeft(1)
            beep(1,800)
            beep(1,400)
            beep(1,800)
            stop()
            break
        elif yellow > .03:
            wall = getObstacle(1)
            print(wall)
            while wall<6200:
                forward(1,0.5)
                wall = getObstacle(1)
            if wall >= 6200:
                turnLeft(1)
                beep(1,800)
                beep(1,400)
                beep(1,800)
                stop()
                break
        else:
            turnLeft(1,.4)