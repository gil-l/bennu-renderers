package pt.ist.fenixWebFramework.renderers;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import pt.ist.fenixWebFramework.renderers.components.HtmlCheckBox;
import pt.ist.fenixWebFramework.renderers.components.HtmlCheckBoxList;
import pt.ist.fenixWebFramework.renderers.components.HtmlComponent;
import pt.ist.fenixWebFramework.renderers.components.HtmlLabel;
import pt.ist.fenixWebFramework.renderers.components.HtmlListItem;
import pt.ist.fenixWebFramework.renderers.components.converters.Converter;
import pt.ist.fenixWebFramework.renderers.contexts.PresentationContext;
import pt.ist.fenixWebFramework.renderers.layouts.Layout;
import pt.ist.fenixWebFramework.renderers.model.MetaObject;
import pt.ist.fenixWebFramework.renderers.model.MetaObjectFactory;
import pt.ist.fenixWebFramework.renderers.model.MetaObjectKey;
import pt.ist.fenixWebFramework.renderers.model.MetaSlot;
import pt.ist.fenixWebFramework.renderers.model.MetaSlotKey;
import pt.ist.fenixWebFramework.renderers.schemas.Schema;
import pt.ist.fenixWebFramework.renderers.utils.RenderKit;
import pt.ist.fenixWebFramework.renderers.utils.RenderMode;
import pt.ist.fenixWebFramework.renderers.utils.RenderUtils;

import org.apache.commons.collections.Predicate;

/**
 * This renderer can be used as the input for a list of objects. The list of
 * objects the user can choose will be presented as an html list were each list
 * item will contain the presentation of the object and a checkbox that allows
 * to choose that particular object. When submiting, all the checked objects
 * will be added to a list and that list will be the value passed to the slot.
 * 
 * <p>
 * Example:
 * <ul>
 * <li><input type="checkbox"/><em>&lt;object A presentation&gt;</em></li>
 * <li><input type="checkbox" checked="checked"/><em>&lt;object B presentation&gt;</em></li>
 * <li><input type="checkbox"/><em>&lt;object C presentation&gt;</em></li>
 * </ul>
 * 
 * @author cfgi
 */
public class CheckBoxOptionListRenderer extends InputRenderer {
    private String eachClasses;

    private String eachStyle;

    private String eachSchema;

    private String eachLayout;

    private String providerClass;

    private DataProvider provider;

    private String sortBy;

    private boolean saveOptions;

    private boolean selectAllShown;
    
    private String checkBoxClasses;
    
    private String checkBoxStyle;
    
    private String listItemClasses;
    
    private String listItemStyle;
    
    private boolean ordered;

    /**
     * This property allows you to configure the class attribute for each
     * object's presentation.
     * 
     * @property
     */
    public void setEachClasses(String classes) {
        this.eachClasses = classes;
    }

    public String getEachClasses() {
        return this.eachClasses;
    }

    /**
     * Allows yout to configure the style attribute for each object's
     * presentation.
     * 
     * @property
     */
    public void setEachStyle(String style) {
        this.eachStyle = style;
    }

    public String getEachStyle() {
        return this.eachStyle;
    }

    public String getEachLayout() {
        return eachLayout;
    }

    /**
     * Allows you to choose the layout in wich each object is to be presented.
     * 
     * @property
     */
    public void setEachLayout(String eachLayout) {
        this.eachLayout = eachLayout;
    }

    public String getEachSchema() {
        return eachSchema;
    }

    /**
     * Allows you to specify the schema that should be used when presenting each
     * individual object.
     * 
     * @property
     */
    public void setEachSchema(String eachSchema) {
        this.eachSchema = eachSchema;
    }

    public String getProviderClass() {
        return this.providerClass;
    }

    /**
     * The class name of a {@link DataProvider data provider}. The data
     * provider is responsible for providing a collection of objects. This
     * collection represents all the possible options for the slot beeing
     * edited. Additionally the data provider can also provide a custom
     * converter for the object encoded values.
     * 
     * <p>
     * Those objects that are already part of the slot's value will make the
     * associated checkbox to be checked. In the example above, the slot
     * contained as value a collection with at least object B but didn't
     * contained object A or C.
     * 
     * @property
     */
    public void setProviderClass(String providerClass) {
        this.providerClass = providerClass;
    }

    public String getSortBy() {
        return this.sortBy;
    }

    /**
     * With this property you can set the criteria used to sort the collection
     * beeing presented. The accepted syntax for the criteria can be seen in
     * {@link RenderUtils#sortCollectionWithCriteria(Collection, String)}.
     * 
     * @property
     */
    public void setSortBy(String sortBy) {
        this.sortBy = sortBy;
    }

    public boolean isSelectAllShown() {
        return this.selectAllShown;
    }

    /**
     * Makes the renderer add and option that selects and unselects all the
     * remaining options.
     * 
     * @property
     */
    public void setSelectAllShown(boolean selectAllShown) {
        this.selectAllShown = selectAllShown;
    }

    public boolean isSaveOptions() {
        return saveOptions;
    }

    /**
     * Allows the possible object list to be persisted between requests, meaning
     * that the provider is invoked only once.
     * 
     * @property
     */
    public void setSaveOptions(boolean saveOptions) {
        this.saveOptions = saveOptions;
    }

    public String getCheckBoxClasses() {
		return checkBoxClasses;
	}

    /**
     * Specifies the class applied to the input element
     * 
     * @property
     */
	public void setCheckBoxClasses(String checkBoxClasses) {
		this.checkBoxClasses = checkBoxClasses;
	}

	public String getCheckBoxStyle() {
		return checkBoxStyle;
	}

    /**
     * Specifies the style applied to the input element
     * 
     * @property
     */
	public void setCheckBoxStyle(String checkBoxStyle) {
		this.checkBoxStyle = checkBoxStyle;
	}

	public String getListItemClasses() {
		return listItemClasses;
	}

    /**
     * Specifies the classes applied to the list element
     * 
     * @property
     */
	public void setListItemClasses(String listItemClasses) {
		this.listItemClasses = listItemClasses;
	}

	public String getListItemStyle() {
		return listItemStyle;
	}

    /**
     * Specifies the style applied to the list element
     * 
     * @property
     */
	public void setListItemStyle(String listItemStyle) {
		this.listItemStyle = listItemStyle;
	}

	public boolean isOrdered() {
		return ordered;
	}

    /**
     * Specifies if the generated list will be ordered
     * 
     * @property
     */
	public void setOrdered(boolean ordered) {
		this.ordered = ordered;
	}

	@Override
    protected Layout getLayout(Object object, Class type) {
        return new CheckBoxListLayout();
    }

    protected DataProvider getProvider() {
        if (this.provider == null) {
            String className = getProviderClass();

            try {
                Class providerCass = (Class<DataProvider>) Class.forName(className);
                this.provider = (DataProvider) providerCass.newInstance();
            } catch (Exception e) {
                throw new RuntimeException("could not get a data provider instance", e);
            }
        }

        return this.provider;
    }

    protected Converter getConverter() {
        DataProvider provider = getProvider();

        return provider.getConverter();
    }

    protected Collection getPossibleObjects() {
        Object object = ((MetaSlot) getInputContext().getMetaObject()).getMetaObject().getObject();
        Object value = getInputContext().getMetaObject().getObject();

        if (getProviderClass() != null) {
            try {
                DataProvider provider = getProvider();
                Collection collection = (Collection) provider.provide(object, value);

                if (getSortBy() == null) {
                    return collection;
                } else {
                    return RenderUtils.sortCollectionWithCriteria(collection, getSortBy());
                }
            } catch (Exception e) {
                throw new RuntimeException("exception while executing data provider", e);
            }
        } else {
            throw new RuntimeException("a data provider must be supplied");
        }
    }

    protected class CheckBoxListLayout extends Layout {

        public HtmlComponent createComponent(Object object, Class type) {
            Collection collection = (Collection) object;

            HtmlCheckBoxList listComponent = new HtmlCheckBoxList();
            listComponent.getList().setOrdered(isOrdered());
            listComponent.setSelectAllShown(isSelectAllShown());

            Schema schema = RenderKit.getInstance().findSchema(getEachSchema());

            List<MetaObject> possibleMetaObjects;

            if (hasSavedPossibleMetaObjects()) {
                possibleMetaObjects = getPossibleMetaObjects();
            } else {
                possibleMetaObjects = new ArrayList<MetaObject>();

                for (Object possibility : getPossibleObjects()) {
                    possibleMetaObjects.add(MetaObjectFactory.createObject(possibility, schema));
                }
            }

            for (MetaObject metaObject : possibleMetaObjects) {
                Object obj = metaObject.getObject();
                MetaObjectKey key = metaObject.getKey();

                String layout = getEachLayout();

                PresentationContext newContext = getContext().createSubContext(metaObject);
                newContext.setLayout(layout);
                newContext.setRenderMode(RenderMode.getMode("output"));

                RenderKit kit = RenderKit.getInstance();
                HtmlComponent component = kit.render(newContext, obj);

                HtmlLabel label = new HtmlLabel();
                label.setBody(component);
                label.setStyle(eachStyle);
                label.setClasses(eachClasses);

                HtmlCheckBox checkBox = listComponent.addOption(label, key.toString());
                label.setFor(checkBox);
                checkBox.setClasses(getCheckBoxClasses());
                checkBox.setStyle(getCheckBoxStyle());

                if (collection != null && collection.contains(obj)) {
                    checkBox.setChecked(true);
                }
            }

            if (isSaveOptions()) {
                savePossibleMetaObjects(possibleMetaObjects);
            }
            
            List<HtmlComponent> components = listComponent.getChildren(new Predicate() {
				public boolean evaluate(Object arg0) {
					return arg0 instanceof HtmlListItem;
				}});
            
            for(HtmlComponent component : components) {
            	HtmlListItem listItem = (HtmlListItem) component;
            	
            	listItem.setStyle(getListItemStyle());
            	listItem.setClasses(getListItemClasses());
            }

            // TODO: make providers only provide a converter for a single object
            // make a wrapper converter that calls that converter for each value
            // this allows converters to be used to menus and checkboxes
            listComponent.setConverter(new OptionConverter(possibleMetaObjects, getConverter()));
            listComponent.setTargetSlot((MetaSlotKey) getInputContext().getMetaObject().getKey());

            return listComponent;
        }

        private boolean hasSavedPossibleMetaObjects() {
            return getInputContext().getViewState().getLocalAttribute("options") != null;
        }

        private List<MetaObject> getPossibleMetaObjects() {
            return (List<MetaObject>) getInputContext().getViewState().getLocalAttribute("options");
        }

        private void savePossibleMetaObjects(List<MetaObject> possibleMetaObjects) {
            getInputContext().getViewState().setLocalAttribute("options", possibleMetaObjects);
        }

    }

    private static class OptionConverter extends Converter {

        private List<MetaObject> metaObjects;

        private Converter converter;

        public OptionConverter(List<MetaObject> metaObjects, Converter converter) {
            this.metaObjects = metaObjects;
            this.converter = converter;
        }

        @Override
        public Object convert(Class type, Object value) {
            String[] textValues = (String[]) value;

            if (textValues == null || textValues.length == 0) {
                return new ArrayList();
            } else {
                if (this.converter != null) {
                    return this.converter.convert(type, value);
                } else {
                    List<Object> result = new ArrayList<Object>();

                    for (MetaObject metaObject : this.metaObjects) {
                        for (int i = 0; i < textValues.length; i++) {
                            String textValue = textValues[i];

                            if (textValue.equals(metaObject.getKey().toString())) {
                                result.add(metaObject.getObject());
                            }
                        }
                    }

                    return result;
                }
            }
        }

    }
}