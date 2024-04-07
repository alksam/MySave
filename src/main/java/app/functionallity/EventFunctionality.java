package app.functionallity;

import app.dao.CategoryDAO;
import app.dto.EventDTO;
import app.model.Category;
import app.model.Event;

public class EventFunctionality {
    private static CategoryDAO categoryDAO;

    public Event convertToEntity(EventDTO eventDTO) {
        Event event = new Event();
        event.setEventId(eventDTO.getEventId());
        event.setTitle(eventDTO.getTitle());
        event.setDescription(eventDTO.getDescription());
        event.setDate(eventDTO.getDate().atStartOfDay()); // Combining LocalDate with start of day for LocalDateTime
        event.setTime(eventDTO.getTime());
        event.setDuration(eventDTO.getDuration());
        event.setCapacity(eventDTO.getCapacity());
        event.setLocation(eventDTO.getLocation());
        event.setInstructor(eventDTO.getInstructor());
        event.setPrice(eventDTO.getPrice());
        event.setCategory(eventDTO.getCategory());
        event.setStatus(eventDTO.getStatus());
        Category category = categoryDAO.findByName(eventDTO.getCategory().getCategoryName());
        if (category == null) {

            category = categoryDAO.save(eventDTO.getCategory());
        }
        event.setCategory(category);

        return event;
    }
}
