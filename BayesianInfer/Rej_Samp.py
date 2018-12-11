import argparse
import random
from XML import *

def SingSampGen(var,prob,sampSing):
    rand = random.random()
    if prob<rand:
        sampSing.update({var:'F'})
    else:
        sampSing.update({var:'T'})


def AllSamGen(BN,itra):
    sampAll = {}
    for i in range(0,itra):
        sampSing = {}
        for var in BN[1]:
            if len(BN[0][var][0]) == 0:
                SingSampGen(var, BN[0][var][1][0], sampSing)
            elif len(BN[0][var][0]) == 1:
                if sampSing[BN[0][var][0][0]] == 'T':
                    SingSampGen(var, BN[0][var][1][0], sampSing)
                elif sampSing[BN[0][var][0][0]] == 'F':
                    SingSampGen(var, BN[0][var][1][1], sampSing)
                else:
                    print "Check!!!"
            elif len(BN[0][var][0]) == 2:
                if sampSing[BN[0][var][0][0]] == 'T' and sampSing[BN[0][var][0][1]] == 'T':
                    SingSampGen(var, BN[0][var][1][0], sampSing)
                elif sampSing[BN[0][var][0][0]] == 'T' and sampSing[BN[0][var][0][1]] == 'F':
                    SingSampGen(var, BN[0][var][1][1], sampSing)
                elif sampSing[BN[0][var][0][0]] == 'F' and sampSing[BN[0][var][0][1]] == 'T':
                    SingSampGen(var, BN[0][var][1][2], sampSing)
                elif sampSing[BN[0][var][0][0]] == 'F' and sampSing[BN[0][var][0][1]] == 'F':
                    SingSampGen(var, BN[0][var][1][3], sampSing)
                else:
                    print "Check!!!"
        sampAll.update({i:sampSing})
    return sampAll


def SamRej(e,sampAll,vars,itra):
    sampRem = {}
    k = 0
    for i in range(0,itra):
        flag = True
        for j in vars:
            if e.has_key(j):
                if e[j] != sampAll[i][j]:
                    flag = False
                    break
        if flag:
            sampRem.update({k:sampAll[i]})
            k+=1
    return sampRem


def infer(x, sampRem):
    num = len(sampRem)
    numT = 0.000
    numF = 0.000
    for i in range(0,num):
        if sampRem[i][x] == 'T':
            numT+=1
        elif sampRem[i][x] == 'F':
            numF+=1
        else:
            print "Check!!!"
    a = round(numT/num,4)
    b = round(numF/num,4)
    result = {'T':a,'F':b}
    return result


if __name__ == '__main__':
    aparser = argparse.ArgumentParser()
    aparser.add_argument('sample', type=int)
    aparser.add_argument('file', type=str)
    aparser.add_argument('query', type=str)
    aparser.add_argument('evidence', type=str, nargs='*')
    args = aparser.parse_args()
    N = args.sample
    BN = {}
    if args.file == 'aima-alarm.xml':
        BN = parser('examples/aima-alarm.xml')
    elif args.file == 'aima-wet-grass.xml':
        BN = parser('examples/aima-wet-grass.xml')
    elif args.file == 'dog-problem.xml':
        BN = parser('examples/dog-problem.xml')
    q = args.query
    e = args.evidence
    ed = {}
    for i in range(0, len(e) / 2):
        if e[2 * i + 1] == 'true':
            ed.update({e[2 * i]: 'T'})
        elif e[2 * i + 1] == 'false':
            ed.update({e[2 * i]: 'F'})
    SampAll = AllSamGen(BN, N)
    SampRem = SamRej(ed, SampAll, BN[1], N)
    infer(q, SampRem)
    print 'The probability of the query variable is ' + str(infer(q, SampRem))