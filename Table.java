import java.util.ArrayList;

public class Table {
	static final int MAXPLAYER = 4;
	private Deck allcards;
	private Player[] allplayers;
	private Dealer dealer;
	private int[] pos_betArray = new int[MAXPLAYER];
	public Table(int nDeck){
		Deck deck =new  Deck(4);
		allcards = deck;
		allplayers=new Player[MAXPLAYER];
	}
	public void set_player(int pos, Player p){
		allplayers[pos]=p;
	}
	public Player[] get_player(){
		return allplayers;
	}
	public void set_dealer(Dealer d){
		dealer=d;
	}
	public Card get_face_up_card_of_dealer(){
		return dealer.getOneRoundCard().get(1);
	}
	private void ask_each_player_about_bets(){
		for(int i =0;i<allplayers.length;i++){
			allplayers[i].say_hello();
			pos_betArray[i]=allplayers[i].make_bet();
		}
	}
	private void distribute_cards_to_dealer_and_players(){
		for(int i =0;i<allplayers.length;i++){
			ArrayList<Card> playerCard=new ArrayList<Card>();
			playerCard.add(allcards.getOneCard(true));
			playerCard.add(allcards.getOneCard(true));
			allplayers[i].setOneRoundCard(playerCard);
		}
		ArrayList<Card> dealerCard=new ArrayList<Card>();
		dealerCard.add(allcards.getOneCard(true));
		dealerCard.add(allcards.getOneCard(true));
		dealer.setOneRoundCard(dealerCard);
		System.out.println("Dealer's face up card is ");
		Card c = new Card(dealer.getOneRoundCard().get(1).getSuit(),dealer.getOneRoundCard().get(1).getRank());
		c.printCard();
		
	}
	private void ask_each_player_about_hits(){
		for(int i =0;i<allplayers.length;i++){
			allplayers[i].hit_me(this);
			boolean hit=false;
			do{hit=allplayers[i].hit_me(this);
				if(hit){
					System.out.println(allplayers[i].get_name()+"'s Cards now:");
					for(Card c : allplayers[i].getOneRoundCard()){
						c.printCard();
					}
					allplayers[i].getOneRoundCard().add(allcards.getOneCard(true));
					System.out.println("Hit! "+allplayers[i].get_name()+"'s Cards now:");
					for(Card c : allplayers[i].getOneRoundCard()){
						c.printCard();
					}
				}
				else{
					System.out.println(allplayers[i].get_name()+"'s Cards now:");
					for(Card c : allplayers[i].getOneRoundCard()){
						c.printCard();
					}
					System.out.println(allplayers[i].get_name()+", Pass hit!");
					System.out.println(allplayers[i].get_name()+" hit is over!");
				}
			}while(hit);
		}
	}
	private void ask_dealer_about_hits(){
		dealer.hit_me(this);
		boolean hit=false;
		do{
			hit=dealer.hit_me(this);
			if(hit){
				dealer.getOneRoundCard().add(allcards.getOneCard(true));
			}
		}while(hit);
		System.out.println("Dealer's hit is over!");
	}
	private void calculate_chips(){
		System.out.println("Dealer's card value is "+dealer.getTotalValue()+" ,Cards:");
		dealer.printAllCard();
		for(int i =0;i<allplayers.length;i++){
			for(Card c : allplayers[i].getOneRoundCard()){
				c.printCard();
				}
			if(dealer.getTotalValue()>21&&allplayers[i].getTotalValue()>21){
				System.out.println(allplayers[i].get_name()+" card value is "+allplayers[i].getTotalValue()+",chips have no change! The Chips now is: "+allplayers[i].get_current_chips());
			}
			else if(dealer.getTotalValue()<21||allplayers[i].getTotalValue()>21){
				allplayers[i].increase_chips(-pos_betArray[i]);
				System.out.println(allplayers[i].get_name()+" card value is "+allplayers[i].getTotalValue()+", Loss "+allplayers[i].make_bet()+" Chips, the Chips now is: "+allplayers[i].get_current_chips());
			}
			else if(dealer.getTotalValue()>21||allplayers[i].getTotalValue()<=21){
				allplayers[i].increase_chips(pos_betArray[i]);
				System.out.println(allplayers[i].get_name()+" card value is "+allplayers[i].getTotalValue()+", Get "+allplayers[i].make_bet()+" Chips, the Chips now is: "+allplayers[i].get_current_chips());
			}
			else if(dealer.getTotalValue()>allplayers[i].getTotalValue()&&dealer.getTotalValue()<=21){
				allplayers[i].increase_chips(-pos_betArray[i]);
				System.out.println(allplayers[i].get_name()+" card value is "+allplayers[i].getTotalValue()+", Loss "+allplayers[i].make_bet()+" Chips, the Chips now is: "+allplayers[i].get_current_chips());
			}
			else if(dealer.getTotalValue()<allplayers[i].getTotalValue()&&dealer.getTotalValue()<22&&allplayers[i].getTotalValue()<22||allplayers[i].getTotalValue()>22){
				allplayers[i].increase_chips(pos_betArray[i]);
				System.out.println(allplayers[i].get_name()+" card value is "+allplayers[i].getTotalValue()+", Get "+allplayers[i].make_bet()+" Chips, the Chips now is: "+allplayers[i].get_current_chips());
			}
			else if(dealer.getTotalValue()<allplayers[i].getTotalValue()&&dealer.getTotalValue()>=21){
				allplayers[i].increase_chips(pos_betArray[i]);
				System.out.println(allplayers[i].get_name()+" card value is "+allplayers[i].getTotalValue()+", Get "+allplayers[i].make_bet()+" Chips, the Chips now is: "+allplayers[i].get_current_chips());
			}
			else if(dealer.getTotalValue()==allplayers[i].getTotalValue()){
				System.out.println(allplayers[i].get_name()+" card value is "+allplayers[i].getTotalValue()+",chips have no change! The Chips now is: "+allplayers[i].get_current_chips());
			}
		}
		
	}
	public int[] get_palyers_bet(){
		return pos_betArray;
	}
	public void play(){
		ask_each_player_about_bets();
		distribute_cards_to_dealer_and_players();
		ask_each_player_about_hits();
		ask_dealer_about_hits();
		calculate_chips();
	}

}
