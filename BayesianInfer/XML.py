import xml.etree.ElementTree as et

def parser(filename):
    tree = et.parse(filename)
    root = tree.getroot()
    bn = {}
    vars = []
    if root.tag == 'BIF':
        root = root[0]
        if root.tag == 'NETWORK':
            for i in range(1, len(root)):
                if root[i].tag == 'VARIABLE':
                    vars.append(root[i][0].text)
                    bn.update({root[i][0].text:[[],[]]})
                elif root[i].tag == 'DEFINITION':
                    for j in range(0,len(root[i])):
                        if root[i][j].tag == 'FOR':
                            var = root[i][j].text
                            if len(root[i]) == 2:
                                if root[i][1].tag == 'TABLE':
                                    val= float(root[i][1].text.split(' ')[0])
                                    bn.update({var:[[],[val]]})
                            elif len(root[i])!=2:
                                for k in range(1,len(root[i])):
                                    if root[i][k].tag == 'GIVEN':
                                        bn[root[i][0].text][0].append(root[i][k].text)
                                    elif root[i][k].tag == 'TABLE':
                                        list = root[i][k].text.strip().split('\n')
                                        if len(list) > 1:
                                            for x in list:
                                                bn[root[i][0].text][1].append(float(x.strip().split(' ')[0]))
                                        elif len(list) == 1:
                                            list2 = list[0].split(' ')
                                            if '' in list2:
                                                list2.remove('')
                                            for n in range(0,len(list2)):
                                                if n%2 ==0:
                                                    bn[root[i][0].text][1].append(float(list2[n]))
    varsTopo = topo(bn,vars)
    return bn,varsTopo


def topo(bn, vars):
    varsTopo = []
    Vars = []
    for var in vars:
        if len(bn[var][0]) == 0:
            varsTopo.append(var)
        else:
            Vars.append(var)
    while len(Vars) != 0:
        for Var in Vars:
            k = 0
            for varstopo in varsTopo:
                if varstopo in bn[Var][0]:
                    k += 1
            if k == len(bn[Var][0]):
                varsTopo.append(Var)
                Vars.remove(Var)
    return varsTopo