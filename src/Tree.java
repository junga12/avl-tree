public interface Tree<T> {
    
    void insert(T data); // 삽입
    void delete(T data); // 삭제
    void traverse();     // 순회
}
