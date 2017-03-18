import edu.princeton.cs.algs4.Stopwatch;

/**
 * Created by magnu on 16/03/2017.
 */
public class HashPipe {

    Pipe root;

    public HashPipe(){
        root = new Pipe(new Pipe[32]);
    } // create an empty symbol table

    public int size(){ return 0; } // return the number of elements

    public void put(String key, Integer val){
        if(containsKey(key)){
            setVal(key, val);
        } else {
            Pipe pipe = new Pipe(key, val);
            for (int i = 0; i < pipe.pointers.length; i++) {
                Pipe floorTemp = floorPipe(pipe.key, i);
                Pipe pointerTemp = floorTemp.pointers[i];
                floorTemp.pointers[i] = pipe;
                pipe.pointers[i] = pointerTemp;
            }
        }
    } // put key-value pair into the table

    private Integer setVal(String key, Integer val){
        return floorPipe(key, 0).pointers[0].val = val;
    }

    private boolean containsKey(String key){
        Pipe temp = floorPipe(key, 0);
        if(temp.key != null) return temp.key.equals(key);
        else return false;
    }

    public Integer get(String key){
        return floorPipe(key, 0).pointers[0].val;
    } // value associated with key

    public String control(String key, int h){
        Pipe temp = floorPipe(key,0);
        if(h < temp.pointers.length) temp = temp.pointers[h];
        else return null;
        if(temp == null) return null;
        else return temp.key;
    }

    public String floor(String key){
        return floorPipe(key,0).key;
    } // largest key less than or equal to key

    private Pipe floorPipe(String key, int height){
        Pipe current = root;
        int i = current.pointers.length - 1;
        boolean found = false;
        while(!found){
            while(current.pointers[i] == null && i > height) i--;
            while (current.pointers[i] != null && current.pointers[i].key.compareTo(key) <= 0) current = current.pointers[i];
            if(current.pointers[i] == null) {
                if(i == height) found = true;
            } else {
                while (current.pointers[i].key.compareTo(key) > 0 && i > height) i--;
                while (current.pointers[i] != null && current.pointers[i].key.compareTo(key) <= 0) current = current.pointers[i];
                if(i == height) found = true;
            }
        }
        return current;
    } // pipe with largest key less than or equal to key

    public class Pipe{
        Integer val;
        String key;
        Pipe[] pointers;

        public Pipe(Pipe[] pointers) {
            this.pointers = pointers;
        }

        public Pipe(String key, Integer val){
            this.key = key;
            this.val = val;
            pointers = new Pipe[Integer.numberOfTrailingZeros(key.hashCode()) + 1];
        }
    }

    public static void main(String[] args) {

        HashPipe HP = new HashPipe();
        HP.put("S",1);
        System.out.println("S");
        HP.put("E",2);
        System.out.println("E");
        HP.put("A",3);
        System.out.println("A");
        HP.put("R",4);
        System.out.println("R");
        HP.put("C",5);
        System.out.println("C");
        HP.put("H",6);
        System.out.println("H");
        HP.put("E",7);
        System.out.println("E");
        HP.put("X",8);
        System.out.println("X");
        HP.put("A",9);
        System.out.println("A");
        HP.put("M",10);
        System.out.println("M");
        HP.put("P",11);
        System.out.println("P");
        HP.put("L",12);
        System.out.println("L");
        HP.put("E",13);
        System.out.println("E");
        System.out.println(HP);
        System.out.println("P: " + HP.control("H",3));
        System.out.println("L: " + HP.control("H",2));
        System.out.println("Null: " + HP.control("P",4));

        // Test
        Stopwatch stopwatch = new Stopwatch();
        int i = 0;
        String [] in = new String[26];
        for(char c = 'A'; c <= 'Z'; c++ ) in[i++] = "" + c;

        HashPipe H = new HashPipe();

        for( int j=0;j<in.length;j++ )
        {
            H.put(in[j], j);
            System.out.print("Insert: ");
            System.out.println(in[j]);
            for( int g=0;g<j;g++ ) {
                for( int h=0;h<32;h++ ) {
                    String ctrl = H.control(in[g],h);
                    if( ctrl != null ) System.out.print(ctrl);
                    else System.out.print(".");
                    System.out.print(" ");
                }
                System.out.print(" : ");
                System.out.println(in[g]);
            }
        }
        System.out.println("Time: " + stopwatch.elapsedTime());

        HashPipe HP1 = new HashPipe();
        HP1.put("A",1);
        System.out.println("A");
        HP1.put("B",2);
        System.out.println("B");
        HP1.put("C",3);
        System.out.println("C");
        String ctrl = HP1.control("A",0);
        System.out.print(ctrl);
        ctrl = HP1.control("A",1);
        System.out.print(ctrl);

    }
}
