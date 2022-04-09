package commands.Arguments;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 *
 * @author HappierGore
 */
public class ArgumentType {

    private final ArgEnum type;
    private boolean optional = false;
    private int position;
    private String name = "";
    private boolean registerInTab = false;
    private String hint = "";
    private List<String> list;

    public ArgumentType(ArgEnum type) {
        this.type = type;
    }

    public ArgumentType setHint(String hint) {
        this.hint = hint;
        return this;
    }

    public ArgEnum getArgType() {
        return this.type;
    }

    public ArgumentType setPosition(int position) {
        this.position = position;
        return this;
    }

    public ArgumentType optional() {
        this.optional = true;
        return this;
    }

    public ArgumentType setName(String name) {
        this.name = name;
        return this;
    }

    public ArgumentType registerInTab() {
        this.registerInTab = true;
        return this;
    }

    public ArgumentType setList(List<String> list) {
        this.list = list;
        return this;
    }

    public List<String> getList() {
        return this.list;
    }

    public boolean isOptional() {
        return this.optional;
    }

    public int getPosition() {
        return position;
    }

    public String getName() {
        return this.name;
    }

    public boolean toTab() {
        return this.registerInTab;
    }

    public String getHint() {
        return this.hint;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("ArgumentType{");
        sb.append("type=").append(type);
        sb.append(", optional=").append(optional);
        sb.append(", position=").append(position);
        sb.append(", name=").append(name);
        sb.append(", registerInTab=").append(registerInTab);
        sb.append(", hint=").append(hint);
        sb.append(", list=").append(list);
        sb.append(", memory=").append(super.toString());
        sb.append('}');
        return sb.toString();
    }

  

}
