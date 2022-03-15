import subprocess
import sys

def componentsFormat(components):
    return list(map(lambda component : component.strip(), components))

def componentsSplitter(components):
    components = componentsFormat(components)
    return components[0], components[1]

def executeCommand(command, parameters):
    process = subprocess.Popen([command, parameters], stdout=subprocess.PIPE, stderr=subprocess.PIPE)
    result, err = process.communicate()
    print(result)
    return str(result, "utf-8")

def parseCommand(command):
    components = command.split()


for command in sys.stdin:
    command = command.strip('\n').strip('(').strip(')')
    components = command.split(',')
    shellCommand, regex = componentsSplitter(components)
    commandExecutionOutput = executeCommand("ls")
    print(commandExecutionOutput)
    #print('/%s/\t%s' %(regex, commandExecutionOutput))
