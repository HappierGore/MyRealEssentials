package com.happiergore.myrealessentials.commands.Arguments;

import java.util.List;

/**
 *
 * @author HappierGore
 */
public class ArgumentType {

    private final ArgEnum type;
    private int position;
    private String name = "";
    private String hint = "";
    private List<String> list;
    private String uniquePermission = "";
    private boolean optional = false;
    private boolean noPermission = false;
    private boolean listToUpperCase = false;
    private boolean listToLowerCase = false;

    public ArgumentType(ArgEnum type) {
        this.type = type;
    }

    public ArgumentType noPermission() {
        this.noPermission = true;
        return this;
    }

    public ArgumentType setHint(String hint) {
        this.hint = hint;
        return this;
    }

    public ArgumentType setUniquePermission(String permission) {
        this.uniquePermission = permission;
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

    public ArgumentType setList(List<String> list) {
        this.list = list;
        return this;
    }
    
    public ArgumentType ListToUpperCase(){
        this.listToUpperCase = true;
        return this;
    }
    
    public ArgumentType ListToLowerCase(){
        this.listToLowerCase = true;
        return this;
    }
    
    public boolean listUpperCase(){
        return this.listToUpperCase;
    }
    
    public boolean listLowerCase(){
        return this.listToLowerCase;
    }

    public boolean usesPermission() {
        return !this.noPermission;
    }

    public String getUniquePermission() {
        return this.uniquePermission;
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
        sb.append(", hint=").append(hint);
        sb.append(", list=").append(list);
        sb.append(", memory=").append(super.toString());
        sb.append('}');
        return sb.toString();
    }

}
