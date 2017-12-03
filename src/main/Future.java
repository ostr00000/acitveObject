package main;

public class Future<T> {
    private T result = null;

    public boolean isAvaliable(){
        return this.result!=null;
    }

    public T getResult(){
        return this.result;
    }

    public void setResult(T result){
        this.result = result;
    }

}
