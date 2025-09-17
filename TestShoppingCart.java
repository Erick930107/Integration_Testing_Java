import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class TestShoppingCart {
	ShoppingCart sc = new ShoppingCart(); // 物件化sc
	
	@BeforeEach
	void setUp(){
		ShoppingCart sc = new ShoppingCart(); // 在每個測試案例調用時都會物件化sc供使用
	}
	
	@AfterEach
	void tearDown(){
		sc = null; // 在每個測試案例結束時都會歸還記憶體
	}
	
	@Test
	void testEmpty(){ 
		sc.addItem(new Product("apple", 30.0));
		sc.empty();                          // 先加一項商品然後empty
		assertEquals(0, sc.getItemCount());  // 確保empty會清空當前購物車，物品數量和價格皆為0
		assertEquals(0.0, sc.getBalance());
	}

	@Test
	void testAddItem(){
		sc.addItem(new Product("apple", 30.0));
		assertEquals(1, sc.getItemCount()); // 確保物品數量和價格皆符合add之商品
        assertEquals(30.0, sc.getBalance());
	}
	
	@Test
	void testRemoveItem() throws ProductNotFoundException{
		Product item = new Product("apple", 30.0);
		Product item2 = new Product("banana", 15.0);
		sc.addItem(item);                   // 加一顆蘋果
		sc.addItem(item2);                  // 加一根香蕉
		sc.removeItem(item);                // 指定移除蘋果，接著觀察購物車是否只剩香蕉
		assertEquals(1, sc.getItemCount()); // 確認只有一件
        assertEquals(15.0, sc.getBalance()); // 卻認為香蕉價格
	}
	
	@Test
	void testRemoveItemNotInCart() throws ProductNotFoundException{
		try{
			sc.removeItem(new Product("apple", 30.0)); // 試著移除購物車內沒有的物品
		}
		catch(ProductNotFoundException ex){
            System.out.println("例外說明：" + ex.getMessage()); // 若try失敗則call exception，防止出現error，也使測試通過
		}
	}
	
	/*
	 整合測試劇本說明：
	 小明帶了100快要去全聯買水果，他先拿了30塊的蘋果，累積30塊，購物車有1件商品；
	 接著拿了60塊的西瓜，累積90塊，購物車有2件商品；
	 後來他拿了15塊的香蕉，累積105塊，購物車有3件商品，
	 然而他結帳前才發現這樣會超出預算，於是把香蕉放回架上，累積90塊，購物車有2件商品，
	 但他還是很想吃香蕉於是改拿兩條10塊的即期芭蕉，累積100塊，購物車有4件商品。
	 */
	@Test
	void testMethod() throws ProductNotFoundException{
		Product apple = new Product("apple", 30.0);
		Product watermelon = new Product("watermelon", 60.0);
		Product banana = new Product("banana", 15.0);
		Product plantain = new Product("plantain", 5.0);
		
		sc.addItem(apple);
		assertEquals(1, sc.getItemCount()); // 購物車有蘋果，共1件，累積30塊
        assertEquals(30.0, sc.getBalance());
        
        sc.addItem(watermelon);
		assertEquals(2, sc.getItemCount()); // 購物車有蘋果、西瓜，共2件，累積90塊
        assertEquals(90.0, sc.getBalance());
        
        sc.addItem(banana);
		assertEquals(3, sc.getItemCount()); // 購物車有蘋果、西瓜、香蕉，共3件，累積105塊
        assertEquals(105.0, sc.getBalance());
        
        sc.removeItem(banana);
        assertEquals(2, sc.getItemCount()); // 購物車有蘋果、西瓜，共2件，累積90塊
        assertEquals(90.0, sc.getBalance());
        
        sc.addItem(plantain);
        sc.addItem(plantain);
		assertEquals(4, sc.getItemCount()); // 購物車有蘋果、西瓜、兩條芭蕉，共4件，累積100塊
        assertEquals(100.0, sc.getBalance());
	}
}
