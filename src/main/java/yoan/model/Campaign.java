package yoan.model;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name="campaign_tbl")
public class Campaign {
	
	@Id@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int campaignID;	
	private String campaignName;
	@OneToMany(cascade=CascadeType.PERSIST)
	@JoinColumn(name ="campaignID")
	private List<Asset> assetID;
	
	public int getCampaignID() {
		return campaignID;
	}
	public void setCampaignID(int campaignID) {
		this.campaignID = campaignID;
	}
	public String getCampaignName() {
		return campaignName;
	}
	public void setCampaignName(String campaignName) {
		this.campaignName = campaignName;
	}
}
