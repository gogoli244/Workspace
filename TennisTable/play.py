# -*- coding: utf-8 -*-

# A played 10.
# B played 15.
# C played 17.
# Who lost the second game?

"""
A = 3
B = 4
C = 3
"""

A = 10
B = 15
C = 17

def printLoserForEachGame(gameList):
    loser = {0:'A', 1:'B', 2:'C'}
    res = ""
    for i in range(len(gameList)):
        res += loser[gameList[i]]
    print(res)

# List playstack is the order of losers for each game.
# Recursively check if the order can match the game numbers of A/B/C.
# If any of the numbers exceed the condition, return False(stop recursive checking),
# else if the numbers just match the condition, return True(one of the solution found),
# else, if it can still be played, continue checking.
def isTimeOk(playstack, loser, players):
    if (players[0] > A or players[1] > B or players[2] > C):
        #print("Failed: %s" % playstack)
        return False
    elif (players[0] == A and players[1] == B and players[2] == C):
        #print("Succeed: %s" % playstack)
        return True
    playstack.append(loser)
    if (loser == 0):
        players[1] += 1
        players[2] += 1
        # If current playtack is ok, just print once.
        if isTimeOk(playstack.copy(), 1, players.copy()):
            printLoserForEachGame(playstack)
        isTimeOk(playstack.copy(), 2, players.copy())
        return
    elif (loser == 1):
        players[0] += 1
        players[2] += 1
        # If current playtack is ok, just print once.
        if isTimeOk(playstack.copy(), 0, players.copy()):
            printLoserForEachGame(playstack)
        isTimeOk(playstack.copy(), 2, players.copy())
        return
    else:
        players[0] += 1
        players[1] += 1
        # If current playtack is ok, just print once.
        if isTimeOk(playstack.copy(), 0, players.copy()):
            printLoserForEachGame(playstack)
        isTimeOk(playstack.copy(), 1, players.copy())
        return

isTimeOk([], 0, [0,0,0])
isTimeOk([], 1, [0,0,0])
isTimeOk([], 2, [0,0,0])
