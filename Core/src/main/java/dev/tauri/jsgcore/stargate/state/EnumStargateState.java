package dev.tauri.jsgcore.stargate.state;

public enum EnumStargateState {
    IDLE(0),

    ENGAGED_INCOMING(1),
    ENGAGED_OUTGOING(2),

    UNSTABLE_OUTGOING(3),
    UNSTABLE_INCOMING(4),

    UNSTABLE_CLOSING(5),

    DIALING_DHD(6),
    DIALING_COMP(7),

    FAILING_DHD(8),
    FAILING_COMP(9);

    public final int id;

    EnumStargateState(int id){
        this.id = id;
    }

    public static EnumStargateState byId(int id){
        for(EnumStargateState state : EnumStargateState.values()){
            if(state.id == id) return state;
        }
        return IDLE;
    }

    // -----------------------------------------------------------------------------

    public boolean isDialing(boolean computer){
        return (computer ? (this == DIALING_COMP) : (this == DIALING_DHD));
    }

    public boolean isDialing(){
        return isDialing(true) || isDialing(false);
    }

    public boolean idle(){
        return this == IDLE;
    }

    public boolean engaged(boolean outgoing){
        return (outgoing ? (this == ENGAGED_OUTGOING) : (this == ENGAGED_INCOMING));
    }

    public boolean engaged(){
        return engaged(true) || engaged(false);
    }

    public boolean failing(boolean computer){
        return (computer ? (this == FAILING_COMP) : (this == FAILING_DHD));
    }

    public boolean failing(){
        return failing(true) || failing(false);
    }

    public boolean unstable(boolean outgoing, boolean closing){
        return (closing ? this == UNSTABLE_CLOSING : (outgoing ? (this == UNSTABLE_OUTGOING) : (this == UNSTABLE_INCOMING)));
    }

    public boolean unstable(boolean closing){
        return unstable(false, closing);
    }

    public boolean unstable(){
        return unstable(true, false) || unstable(false, false) || unstable(true);
    }
}
