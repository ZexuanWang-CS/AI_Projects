import argparse
import random
from Enum_Infer import *
from XML import *

def Gibbs(x,e,BN,itra):
    res = {'T':0.00,'F':0.00}
    Non_e = []
    currSamp = {}
    for var in BN[1]:
        if not e.has_key(var):
            Non_e.append(var)
            if random.randint(1,3) == 1:
                currSamp.update({var: 'T'})
            else:
                currSamp.update({var: 'F'})
        else:
            currSamp.update({var:e[var]})
    for k in range(0,itra):
        for var in Non_e:
            MB = Markov(var,BN,currSamp)
            prob = enumeration_ask(var,MB,BN)
            rand = random.random()
            if prob['T'] < rand:
                currSamp.update({var: 'F'})
            else:
                currSamp.update({var: 'T'})
            if currSamp[x] == 'T':
                T = res['T'] + 1
                res.update({'T':T})
            else:
                F = res['F'] + 1
                res.update({'F': F})
    Res = normalize(res)
    return Res


def Markov(var,BN,currSamp):
    mb = []
    MB = {}
    if BN[0][var][0] is not None:
        for par in BN[0][var][0]:
            mb = Append(mb,par)
    for Var in BN[1]:
        if var in BN[0][Var][0]:
            mb = Append(mb, Var)
            for VAR in BN[0][Var][0]:
                if not VAR == var:
                    mb = Append(mb, VAR)
    for mb_ele in mb:
        MB.update({mb_ele:currSamp[mb_ele]})
    return MB


def Append(mb,ele):
    if ele not in mb:
        mb.append(ele)
    return mb


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
    print 'The probability of the query variable is ' + str(Gibbs(q, ed, BN, N))