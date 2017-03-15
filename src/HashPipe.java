import edu.princeton.cs.algs4.StdIn;

/**
 * Created by magnu on 14/03/2017.
 */
public class HashPipe {

    private Pipe root;

    public HashPipe(){
        root = new Pipe(new Pipe[8]);
    }

    public int size(){
        return root.pointers.length;
    }

    public void put(String key, Integer val){
        Pipe pipe = new Pipe(key,val);
        for(int i = 0; i < pipe.pointers.length; i++){
            Pipe temp = floorPipe(pipe.key,i).pointers[i];
            floorPipe(pipe.key,i).pointers[i] = pipe;
            pipe.pointers[i] = temp;
        }
    }

    public Integer get(String key){
        int i = root.pointers.length - 1;
        Pipe current = root.pointers[i];
        while(!current.key.equals(key)){
            while(current.pointers[i].key.compareTo(key) > 0 || current.pointers[i] == null) {
                i--;
            }
            current = current.pointers[i];
        }
        return current.val;
    }

    public String floor(String key){
        return null;
    }

    private Pipe floorPipe(String key, int height){
        int i = root.pointers.length - 1;
        Pipe current = root;
        while(i != height && (current.key.compareTo(key) < 0)){
            while(current.pointers[i].key.compareTo(key) > 0 || current.pointers[i] == null) {
                i--;
            }
            current = current.pointers[i];
        }
        return current;
    }

    public String control(String key, int h){
        int i = root.pointers.length - 1;
        Pipe current = root.pointers[i];
        while(!current.key.equals(key)){
            while(current.pointers[i].key.compareTo(key) > 0 || current.pointers[i] == null) {
                i--;
            }
            current = current.pointers[i];
        }
        return current.pointers[h].key;
    }

    private class Pipe{
        Integer val;
        String key;
        Pipe[] pointers;

        public Pipe(Pipe[] pointers) {
            this.pointers = pointers;
        }

        public Pipe(String key, Integer val){
            this.key = key;
            this.val = val;
            pointers = new Pipe[Integer.numberOfTrailingZeros(key.hashCode() + 1)];
        }
    }

    public static void main(String[] args) {
        HashPipe HP = new HashPipe();
        for(int i = 0; i < 12; i++){
            HP.put(StdIn.readString(), i);
        }
        System.out.println("P: " + HP.control("H",3));
        System.out.println("L: " + HP.control("H",2));
        System.out.println("Null: " + HP.control("P",4));
    }
}
