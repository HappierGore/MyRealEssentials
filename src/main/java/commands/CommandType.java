package commands;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 *
 * @author HappierGore
 */
public class CommandType {

    private boolean allowTargeting = false;
    private int targetPosition = 0;
    private int argsAmount = 0;
    private final Map<Integer, Set<String>> argsPerPosition;

    public CommandType(int argsAmount, Map<Integer, Set<String>> argsPerPosition) {
        this.argsAmount = argsAmount;
        this.argsPerPosition = argsPerPosition != null ? argsPerPosition : new HashMap<>();
    }

    public CommandType allowTargeting(int targetPosition) {
        this.allowTargeting = true;
        this.targetPosition = targetPosition;
        return this;
    }

    public boolean allowsTarget() {
        return this.allowTargeting;
    }

    public int targetPosition() {
        return this.targetPosition;
    }

    public int getArgsAmount() {
        return this.argsAmount;
    }

    public Map<Integer, Set<String>> getArgsPerPosition() {
        return argsPerPosition;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("CommandType{");
        sb.append("allowTargeting=").append(allowTargeting);
        sb.append(", targetPosition=").append(targetPosition);
        sb.append(", argsAmount=").append(argsAmount);
        sb.append(", argsPerPosition=").append(argsPerPosition);
        sb.append('}');
        return sb.toString();
    }
    
    

}
