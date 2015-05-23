import platform 

if platform.system() != 'Windows':
    class colors:
        HEADER         = '\033[95m'
        BLUE           = '\033[94m'
        DEBUG          = '\033[92m'
        WARNING        = '\033[93m'
        FAIL           = '\033[91m'
        ENDC           = '\033[0m'
        BOLD           = '\033[1m'
        UNDERLINE      = '\033[4m'
else:
    class colors:
        HEADER         = ''
        BLUE           = ''
        DEBUG          = ''
        WARNING        = ''
        FAIL           = ''
        ENDC           = ''
        BOLD           = ''
        UNDERLINE      = ''

class LogLevel:
    OFF      = 0
    VERBOSE  = 1
    DEBUG    = 2
    WARNING  = 3
    CRITICAL = 4
    REQUIRED = 5


class Log:

    """A simple Logger for python """

    def __init__(self,_logTag):
        """ Takes a logTag for the logger instance """
        self.logLevel = LogLevel()
        self.logTag = _logTag
        self.alternater=0

    def debug(self, log):
        """
        Logs all debug logs
        """
        print colors.DEBUG + self.logTag + " :: "+ log  + colors.ENDC
        return

    def warning(self, log):
        """
        Logs all warning logs
        """
        print colors.WARNING + self.logTag + " :: "+ log  + colors.ENDC
        return

    def critical(self, log):
        """
        Logs critical logs
        """
        print colors.FAIL + self.logTag + " :: "+ log  + colors.ENDC
        return

    def verbose(self, log):
        """
        Logs verbose logs
        """
        self.alternater = self.alternater+1
        if(self.alternater%2 == 0):
            print self.logTag + " :: "+ log
        else:
            print colors.BLUE + self.logTag + " :: "+ log  + colors.ENDC
        return

    def required(self, log):
        """
        Logs required logs
        """
        if(self.alternater%2 == 0):
            print  colors.BOLD + self.logTag + " :: "+ log  + colors.ENDC
        else:
            print colors.HEADER + self.logTag + " :: "+ log  + colors.ENDC
        self.alternater = self.alternater+1
        return
