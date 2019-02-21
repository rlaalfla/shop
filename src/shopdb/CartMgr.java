package shopdb;
import java.util.Hashtable;//Hashtable:��ٱ��Ϸ� ����Ϸ���


public class CartMgr {

	private Hashtable hcart=new Hashtable();//��ü����
	
	public CartMgr(){}//������
	
	//��ٱ��Ͽ� �ֱ�, cart�� �ֱ�
	public void addCart(OrderDto orderDto){
		//System.out.println("��ٱ��� �ֱ�");
		
		//�󼼺��⿡�� �Ѿ�� ������
		int pro_no=orderDto.getPro_no();//��ǰ�Ϸù�ȣ
		String q=orderDto.getQuantity();//�ֹ�����
		int quantity=Integer.parseInt(q);//�ֹ������� ������
		
		if(quantity>0){//�ֹ� ������ ������
			if(hcart.containsKey(pro_no)){//�߰��ֹ�
	
				//hcart�� pro_no ��ǰ ��ȣ�� ������
				OrderDto tempOrder=(OrderDto)hcart.get(pro_no);
				quantity += Integer.parseInt(tempOrder.getQuantity());//�����߰�
				tempOrder.setQuantity(quantity+"");//���� setter
				hcart.put(pro_no, tempOrder);//�ؽ����̺�(��ٱ���)�� �ִ´�
				//			key,value
				
			}else{//pro_no���� ���:�� ��ǰ�� ó�� ��� ���
				hcart.put(pro_no, orderDto);
				
			}//else end
		}//if
		
	}//addCard() end------------------------
	
	
	//----------------------------------
	//cart ���� ���� = ��ٱ��Ͽ� �ִ� ���ǵ� ����Ʈ
	//----------------------------------
	public Hashtable getCartList(){
		//System.out.println("��ٱ��� ����Ʈ");
		return hcart;
	}//getCartList() end
	
	
	
	//---------------------------------
	//cart���� ����(��ٱ��� ���� ����)
	//---------------------------------
	public void updateCart(OrderDto order){
		int pro_no=order.getPro_no();//��ǰ��ȣ
		hcart.put(pro_no, order);//�ؽ� ���̺�(key,value)�� �ִ´�
		//										Object
	}//updateCart() end
	
	
	
	//---------------------------------
	//cart ���� ����
	//---------------------------------
	public void deleteCart(OrderDto order){
		int pro_no=order.getPro_no();//��ǰ��ȣ
		hcart.remove(pro_no);//�ؽ����̺�(��ٱ���)���� ����
	}//deleteCart() end
	
	
}//class
