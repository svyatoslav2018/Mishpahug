package telran.ashkelon2018.mishpahug.domain;

import java.time.LocalDateTime;      
import java.util.HashSet;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Setter;
@NoArgsConstructor
@Getter
@EqualsAndHashCode(of = {"id"})
@ToString


public class Event {
		//String id;
		@Setter String title;
		@Setter String holiday;
		String address;
		String eventConfession;
		String typeOfKitchen;
		String aboutEvent;
		@JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm")
		LocalDateTime dateFrom;
		@JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm")
		LocalDateTime dateTo;
		
		
		
		Set<LocalDateTime> datesFrom;
		Set<LocalDateTime> datesTo;
		Set<String> holidays;
		Set<String> eventConfessions;
		Set<String>
		
		
		
	
		Set<Comment> comments;
		

		
		
		public Post( String title, String content, String author, Set<String> tags) {
			this.title = title;
			this.content = content;
			this.author = author;
			this.tags = tags;
			dateCreated = LocalDateTime.now();
			comments = new HashSet<>();
		}
		
		public void addLike() {
			likes++;
		}
		
		public boolean addComment(Comment comment) {
			return comments.add(comment);
		}
		
		public boolean addTag(String tag) {
			return tags.add(tag);
		}
		
		public boolean removeTag(String tag) {
			return tags.remove(tag);
		}
	
}
