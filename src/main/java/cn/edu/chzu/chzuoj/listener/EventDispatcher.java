package cn.edu.chzu.chzuoj.listener;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;

/**
 * 
 * @author dzj0821
 *
 */
public class EventDispatcher {
	private static EventDispatcher instance = new EventDispatcher();
	private HashMap<EventType, LinkedList<BaseEventListener>> map = new HashMap<EventType, LinkedList<BaseEventListener>>();
	private EventType dispatchType = null;
	private LinkedList<BaseEventListener> willAddListeners = new LinkedList<BaseEventListener>();
	private LinkedList<BaseEventListener> willRemoveListeners = new LinkedList<BaseEventListener>();
	
	private EventDispatcher() {}
	
	public static EventDispatcher getInstance() {
		return instance;
	}
	
	public void addEventListener(EventType type, BaseEventListener listener) {
		if (type == null || listener == null) {
			return;
		}
		if (dispatchType == type) {
			willAddListeners.add(listener);
			return;
		}
		LinkedList<BaseEventListener> list = map.get(type);
		if (list == null) {
			list = new LinkedList<BaseEventListener>();
			map.put(type, list);
		}
		list.add(listener);
	}
	
	public void removeEventListener(EventType type, BaseEventListener listener) {
		if (type == null || listener == null) {
			return;
		}
		if (dispatchType == type) {
			willRemoveListeners.add(listener);
			return;
		}
		LinkedList<BaseEventListener> list = map.get(type);
		if (list == null) {
			return;
		}
		list.remove(listener);
	}
	
	public void dispatchEvent(HttpServletRequest request, HttpServletResponse response, Map<String, String> params, ModelAndView modelAndView, EventType type, boolean success) {
		dispatchType = type;
		LinkedList<BaseEventListener> list = map.get(type);
		if (list == null) {
			return;
		}
		for (BaseEventListener baseEventListener : list) {
			baseEventListener.onEvent(request, response, params, modelAndView, type, success);
		}
		dispatchType = null;
		for (BaseEventListener baseEventListener : willAddListeners) {
			addEventListener(type, baseEventListener);
		}
		for (BaseEventListener baseEventListener : willRemoveListeners) {
			removeEventListener(type, baseEventListener);
		}
	}
}
