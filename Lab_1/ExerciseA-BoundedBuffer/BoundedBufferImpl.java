public class BoundedBufferImpl {

    private Object buffer[];
    private Integer current = 0;
    private Integer maxIndex = 0;

    private static final Integer DEFAULT_SIZE = 10;

    public BoundedBufferImpl() {
        this(DEFAULT_SIZE);
    }

    /**
     * Creates a BoundedBuffer with the specified size.
     * 
     * @param size Maximum number of elements that fits in the buffer
     */
    public BoundedBufferImpl(int size) {
        this.buffer = new Object[size];
        this.maxIndex = size - 1;
    }

    /**
     * Append an element into the buffer.
     * 
     * @param o The new object
     */
    public synchronized void insert(Object o) {
        while(true) {
            try {
                while(this.current > this.maxIndex) {
                    wait();
                }
            } catch(Exception e) {}            

            this.buffer[this.current] = o;
            this.current = this.current + 1;

            notifyAll();
            break;
        }
    }

    /**
     * Pop and return the first element of the buffer.
     * 
     * @return The first element
     */
    public synchronized Object remove() {
        while(true) {
            try {
                while(this.buffer[0] == null) {
                    wait();
                }
            } catch(Exception e) {}       

            Object output = this.buffer[0];
            this.buffer = this.rotateLeft(this.buffer);
            this.current = this.current == 0 ? 0 : this.current - 1;

            notifyAll();
            return output;
        }
    }

    /**
     * Indiscriminately rotate the given array left by 1
     * 
     * @param a The array
     * @return The array rotated left by 1
     */
    private synchronized Object[] rotateLeft(Object[] a) {
        for(int i = 0; i < a.length; i++) {
            try {
                a[i] = a[i + 1];
            } catch(Exception e) {
                continue;
            }
        }
        return a;
    }
}