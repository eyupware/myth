package gov.babalar.module;


import gov.babalar.Myth;
import gov.babalar.helpers.Mapper;

/**
 * ----------
 * 9/3/2023
 * 3:00 AM
 * ----------
 **/
public abstract class Module implements Runnable {
    private String name;
    private Category category;
    private boolean enabled;
    private int key;
    public abstract void onEnable();
    public abstract void onDisable();
    public void toggle()
    {
        this.enabled = !enabled;
        if(this.enabled) {
            onEnable();
            Myth.bus.register(this);
            // Mapper.addChatMessage(String.format("%s is §aenabled" , this.getName()));
        } else {
            // Mapper.addChatMessage(String.format("%s is §cdisabled", this.getName()));
            Myth.bus.unregister(this);
            onDisable();
        }
    }

    public Module(String name, Category category, int key) {
        this.name = name;
        this.category = category;
        this.key = key;
        this.enabled = false;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public int getKey() {
        return key;
    }

    public void setKey(int key) {
        this.key = key;
    }
}