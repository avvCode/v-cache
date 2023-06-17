package com.vv.server.dispatch;



import com.vv.server.command.HelpCommand;
import com.vv.server.command.PingCommand;
import com.vv.server.util.stringUtils.StringUtils;

import java.util.Locale;


public class DispatchCommand {

    public static String dispatchCommand(String message){
        String[] parseMessage = StringUtils.parse(message);
        String result;
        switch (parseMessage[0].toUpperCase(Locale.ROOT)){
            case HelpCommand
                    .HELP:
                result = HelpCommand.doCommand(parseMessage);
                break;
            case PingCommand
                    .PING:
                result = PingCommand.doCommand();
                break;
            default:
                result = String.valueOf(DoCommand.doCommand(parseMessage));
                break;
        }
        return result;
    }
}
