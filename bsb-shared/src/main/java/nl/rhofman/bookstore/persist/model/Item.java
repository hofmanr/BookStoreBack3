package nl.rhofman.bookstore.persist.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import nl.rhofman.persist.model.BaseEntityVersion;

import java.io.Serializable;

@Entity(name = "Item")
@DiscriminatorColumn(name = "discriminator", discriminatorType = DiscriminatorType.CHAR)
@DiscriminatorValue("I")
@NamedQueries({
        @NamedQuery(name = Item.FIND_TOP_RATED, query = "SELECT i FROM Item i WHERE i.id in :ids"),
        @NamedQuery(name = Item.SEARCH, query = "SELECT i FROM Item i WHERE UPPER(i.title) LIKE :keyword OR UPPER(i.description) LIKE :keyword ORDER BY i.title")

})
public class Item extends BaseEntityVersion implements Serializable {
    private static final long serialVersionUID = 367291203462534l;

    // ======================================
    // =             Constants              =
    // ======================================

    public static final String FIND_TOP_RATED = "Item.findTopRated";
    public static final String SEARCH = "Item.search";

    // ======================================
    // =             Attributes             =
    // ======================================

    @Column(name = "title", length = 200, nullable = false)
    @NotNull
    @Size(min = 1, max = 200)
    protected String title;

    @Column(name = "description", length = 10000)
    @Size(min = 1, max = 10000)
    protected String description;

    @Column(name = "unit_cost")
    @Min(1)
    protected Float unitCost;

    @Column(name = "item_rank") // rank is a preserved word in MySQL
    protected Integer rank;

    @Column(name = "small_image_url")
    protected String smallImageUrl;

    @Column(name = "image_url")
    protected String imageUrl;

    // ======================================
    // =        Getters and Setters         =
    // ======================================

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Float getUnitCost() {
        return unitCost;
    }

    public void setUnitCost(Float unitCost) {
        this.unitCost = unitCost;
    }

    public Integer getRank() {
        return rank;
    }

    public void setRank(Integer rank) {
        this.rank = rank;
    }

    public String getSmallImageUrl() {
        return smallImageUrl;
    }

    public void setSmallImagUrl(String smallImageUrl) {
        this.smallImageUrl = smallImageUrl;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    // ======================================
    // =   Methods hash, equals, toString   =
    // ======================================

    @Override
    public String toString() {
        String result = getClass().getSimpleName() + " ";
        if (id != null)
            result += "id: " + id;
        result += ", version: " + version;
        if (title != null && !title.trim().isEmpty())
            result += ", title: " + title;
        if (description != null && !description.trim().isEmpty())
            result += ", description: " + description;
        if (unitCost != null)
            result += ", unitCost: " + unitCost;
        return result;
    }

}
