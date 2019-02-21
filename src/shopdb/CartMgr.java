package shopdb;
import java.util.Hashtable;//Hashtable:장바구니로 사용하려고


public class CartMgr {

	private Hashtable hcart=new Hashtable();//객체생성
	
	public CartMgr(){}//생성자
	
	//장바구니에 넣기, cart에 넣기
	public void addCart(OrderDto orderDto){
		//System.out.println("장바구니 넣기");
		
		//상세보기에서 넘어온 데이터
		int pro_no=orderDto.getPro_no();//상품일련번호
		String q=orderDto.getQuantity();//주문수량
		int quantity=Integer.parseInt(q);//주문수량을 정수로
		
		if(quantity>0){//주문 수량이 있으면
			if(hcart.containsKey(pro_no)){//추가주문
	
				//hcart에 pro_no 상품 번호가 있으면
				OrderDto tempOrder=(OrderDto)hcart.get(pro_no);
				quantity += Integer.parseInt(tempOrder.getQuantity());//수량추가
				tempOrder.setQuantity(quantity+"");//수량 setter
				hcart.put(pro_no, tempOrder);//해쉬테이블(장바구니)에 넣는다
				//			key,value
				
			}else{//pro_no없는 경우:이 상품을 처음 사는 경우
				hcart.put(pro_no, orderDto);
				
			}//else end
		}//if
		
	}//addCard() end------------------------
	
	
	//----------------------------------
	//cart 내용 리턴 = 장바구니에 있는 물건들 리스트
	//----------------------------------
	public Hashtable getCartList(){
		//System.out.println("장바구니 리스트");
		return hcart;
	}//getCartList() end
	
	
	
	//---------------------------------
	//cart내용 수정(장바구니 내용 수정)
	//---------------------------------
	public void updateCart(OrderDto order){
		int pro_no=order.getPro_no();//상품번호
		hcart.put(pro_no, order);//해쉬 테이블(key,value)에 넣는다
		//										Object
	}//updateCart() end
	
	
	
	//---------------------------------
	//cart 내용 제거
	//---------------------------------
	public void deleteCart(OrderDto order){
		int pro_no=order.getPro_no();//상품번호
		hcart.remove(pro_no);//해쉬테이블(장바구니)에서 제거
	}//deleteCart() end
	
	
}//class
