package Application;

public class ArrayList<T> {
	T[] arr;
	int size = 0;

	public ArrayList(int size) {

		this.arr = (T[]) new Object[size];
	}
	
	public ArrayList() {
		
	}
	
	public void add(T data) {
		if (size >=arr.length) 
			reSize();
			arr[size++] = data;

	}

	public void delete(T data) {
		for (int i = 0; i < size; i++) {
			if (arr[i].equals(data)) {
				for (int j = i + 1; j < size; j++) {
					arr[j - 1] = arr[j];
				}
				size--;
			}
		}
	}
	public void print() {
		for (int i = 0; i < size; i++) {
			System.out.println(arr[i]);
		}

	}
	public T get(int index) {
		if ( index < size && index >=0){
			return arr[index];
		}
		return null;
	}
     public void set(int index, T data) {
	        if (index < 0 || index >= size) {
	            throw new IndexOutOfBoundsException("Index out of bounds");
	        }
	        arr[index] = data;
	   }

    public boolean contains(T data) {
        for (int i = 0; i < size; i++) {
            if (arr[i].equals(data)) {
                return true;
            }
        }
        return false;
    }

	public int size() {
		return size;
	}

	private void reSize() {
		Object[] temp = new Object[2 * arr.length];
		System.arraycopy(arr, 0, temp, 0, size);
		arr = (T[]) temp;
	}

}
