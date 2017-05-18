package com.vascomouta.VMLogger.implementation;

import com.vascomouta.VMLogger.LogConfiguration;

/**
 * Created by Sourabh Kapoor  on 16/05/17.
 */

public class RootLogConfiguration extends BaseLogConfiguration {


    public static String ROOT_IDENTIFIER: String = "root";
    public static String DOT = ".";


    public LogConfiguration getChildren(String identifier , BaseLogConfiguration type){
        String name = identifier;
        LogConfiguration parent = this;
        while (true){
            if(parent.getChildren()){

            }else{

            }
        }

    }


    internal func getChildren(_ identifier: String, type: BaseLogConfiguration.Type ) -> LogConfiguration {
        var name = identifier
        var parent: LogConfiguration = self
        while (true) {
            if let child = parent.getChildren(name) {
                return child
            } else {
                var tree: String? = nil
                if let range = name.range(of: RootLogConfiguration.DOT) {
                    tree = name.substring(from: range.upperBound)
                    name = name.substring(to: range.lowerBound)
                    if let child = parent.getChildren(name) {
                        parent = child
                        name = tree!
                        continue
                    }
                }
                let child = type.init(name, assignedLevel: nil, parent: parent, appenders: [], synchronousMode: synchronousMode)
                parent.addChildren(child, copyGrandChildren:false)
                guard tree != nil else { return child }
                parent = child
                name = tree!
            }

        }

}
