package commands;

import commands.Arguments.ArgumentType;
import commands.Arguments.ArgEnum;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 *
 * @author HappierGore
 */
public class CommandType {

    private final Set<ArgumentType> args;

    public CommandType(Set<ArgumentType> argsPerPosition) {
        this.args = argsPerPosition;
    }

    public Set<ArgumentType> getArgs() {
        return this.args;
    }

    public Set<ArgumentType> getArgsInPosition(int position) {
        Set<ArgumentType> result = new HashSet<>();
        this.args.forEach(arg -> {
            if (arg.getPosition() == position) {
                result.add(arg);
            }
        });
        return result;
    }

    public Set<String> getArgsNamesInPosition(int position) {
        Set<String> result = new HashSet<>();
        this.args.forEach(arg -> {
            if (arg.getPosition() == position) {
                result.add(arg.getName());
            }
        });
        return result;
    }

    public int getArgsSize() {
        Map<Integer, Set<ArgumentType>> position = new HashMap<>();

        for (ArgumentType ar : this.args) {
            if (position.containsKey(ar.getPosition())) {
                position.get(ar.getPosition()).add(ar);
            } else {
                position.put(ar.getPosition(), new HashSet<ArgumentType>() {
                    {
                        add(ar);
                    }
                });
            }
        }
        return position.size();
    }

    public boolean allowsTarget() {
        boolean result = false;
        for (ArgumentType arg : this.args) {
            if (arg.getArgType() == ArgEnum.target) {
                result = true;
            }
        }
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("CommandType{");
        sb.append(", args=").append(args);
        sb.append('}');
        return sb.toString();
    }

}
