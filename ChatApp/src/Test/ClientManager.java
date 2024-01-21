//package Test;
//
//import java.util.HashMap;
//import java.util.Map;
//
//class ClientData {
//	private String id;
//	private String name;
//	private String email;
//
//	public ClientData(String id, String name, String email) {
//		this.id = id;
//		this.name = name;
//		this.email = email;
//	}
//
//	public String getId() {
//		return id;
//	}
//
//	public void setId(String id) {
//		this.id = id;
//	}
//
//	public String getName() {
//		return name;
//	}
//
//	public void setName(String name) {
//		this.name = name;
//	}
//}
//
//class SocketTest {
//	private String id;
//	
//	public SocketTest(String id) {
//		this.id = id;
//	}
//
//	public String getId() {
//		return id;
//	}
//
//	public void setId(String id) {
//		this.id = id;
//	}
//}
//
//
//public class ClientManager {
//		private Map<SocketTest, ClientData> clientDataMap = new HashMap<>();
//
//		// Hàm thêm ClientData vào HashMap
//		public void addClientData(ClientData clientData, SocketTest socket) {
//				clientDataMap.put(socket, clientData);
//		}
//
//		// Hàm tìm kiếm ClientData theo sendId
//		public ClientData getClientDataBySendId(ClientData clientData) {
//				return clientDataMap.get(clientData.getId());
//		}
//
//
//	public static void main(String[] args) {
//		ClientManager clientDataManager = new ClientManager();
//
//		// Thêm các ClientData vào HashMap
//		clientDataManager.addClientData(new ClientData("1", "client 1", "a@gmail.com"), new SocketTest("123123123"));
//		
//		clientDataManager.addClientData(new ClientData("2", "client 2", "b@gmail.com"), new SocketTest("asdfhui23-2"));
//		
//		clientDataManager.addClientData(new ClientData("5", "client 5", "c@gmail.com"), new SocketTest("099j90ji"));
//
//		// Tìm kiếm ClientData với sendId
//		Clientdata
//		ClientData foundClientData = clientDataManager.getClientDataBySendId(ClientManager.get);
//
//		if (foundClientData != null) {
//				System.out.println("ClientData found: " + foundClientData);
//		} else {
//				System.out.println("ClientData not found for sendId: " + sendIdToSearch);
//		}
//	}
//}
//	
//
//	