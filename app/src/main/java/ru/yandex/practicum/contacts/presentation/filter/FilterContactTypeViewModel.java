package ru.yandex.practicum.contacts.presentation.filter;

import static ru.yandex.practicum.contacts.model.ContactType.getContactTypes;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import ru.yandex.practicum.contacts.model.ContactType;
import ru.yandex.practicum.contacts.presentation.base.BaseBottomSheetViewModel;
import ru.yandex.practicum.contacts.presentation.filter.model.FilterContactType;
import ru.yandex.practicum.contacts.presentation.filter.model.FilterContactTypeUi;
import ru.yandex.practicum.contacts.utils.model.ContactTypeUtils;
import ru.yandex.practicum.contacts.utils.model.FilterContactTypeUtils;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

public class FilterContactTypeViewModel extends BaseBottomSheetViewModel {

    private final UiState uiState = new UiState();
    private final MutableLiveData<List<FilterContactTypeUi>> filterContactTypesLiveDate = new MutableLiveData<>();
    private final MutableLiveData<UiState> uiStateLiveDate = new MutableLiveData<>();

    private Set<String> defaultFilterContactTypes;
    private Set<String> selectedFilterContactTypes;

    public void init(Set<String> defaultFilterContactTypes) {
        this.defaultFilterContactTypes = new HashSet<>(defaultFilterContactTypes);
        this.selectedFilterContactTypes = new HashSet<>(defaultFilterContactTypes);
        updateFilterContactTypes();
        updateUiState();
    }

    public void onFilterTypeItemClick(FilterContactTypeUi filterContactType) {
        updateSelectedContactTypes(filterContactType.getContactType());
        updateFilterContactTypes();
        updateUiState();
    }

    @Override
    public void onApplyClick() {
        uiState.newSelectedContactTypes = selectedFilterContactTypes;
        updateUiState();
    }

    @Override
    public void onResetClick() {
        selectedFilterContactTypes = new HashSet<>(defaultFilterContactTypes);
        updateFilterContactTypes();
        updateUiState();
    }

    public MutableLiveData<List<FilterContactTypeUi>> getFilterContactTypesLiveDate() {
        return filterContactTypesLiveDate;
    }

    public MutableLiveData<UiState> getUiStateLiveDate() {
        return uiStateLiveDate;
    }

    private void updateFilterContactTypes() {
        String[] contactTypes = ContactType.getContactTypes();
        ArrayList<FilterContactTypeUi> filterContactTypeUiList = new ArrayList<>();

        filterContactTypeUiList.add(createAllSelectedItem(contactTypes));

        for (String type : contactTypes) {
            filterContactTypeUiList.add(createFilterContactType(type));
        }

        filterContactTypesLiveDate.setValue(filterContactTypeUiList);
    }

    // создайте массив строк. Для инициализации используйте метод ContactType.getContactTypes(),
        // который должен возвращать список всех доступных источников контактов

        // создайте список типа FilterContactTypeUi и заполните его с помощью цикла forEach
        // forEach должен бежать по строковому массиву, который вы создали ранее

        // вызовите меотод setValue() у переменной filterContactTypesLiveDate и передайте в качестве аргументы ваш список типа FilterContactTypeUi


    @NonNull
    private FilterContactTypeUi createAllSelectedItem(final String[] types) {
        final boolean allSelected = selectedFilterContactTypes.size() == types.length;

        return new FilterContactTypeUi(FilterContactType.ALL, allSelected);
    }

    @NonNull
    private FilterContactTypeUi createFilterContactType(final String type) {
        return new FilterContactTypeUi(
                ContactTypeUtils.toFilterContactType(type),
                selectedFilterContactTypes.contains(type)
        );
    }

    private void updateUiState() {
        uiState.isApplyEnable = !defaultFilterContactTypes.equals(selectedFilterContactTypes) && !selectedFilterContactTypes.isEmpty();
        uiStateLiveDate.setValue(uiState);
    }

    private void updateSelectedContactTypes(FilterContactType type) {
        if (type == FilterContactType.ALL) {
            if (selectedFilterContactTypes.size() == getContactTypes().length) {
                selectedFilterContactTypes.clear();
            } else {
                selectedFilterContactTypes.addAll(Arrays.asList(getContactTypes()));
            }
            return;
        }
        final String contactType = FilterContactTypeUtils.toContactType(type);
        if (selectedFilterContactTypes.contains(contactType)) {
            selectedFilterContactTypes.remove(contactType);
        } else {
            selectedFilterContactTypes.add(contactType);
        }
    }

    static class UiState {

        public boolean isApplyEnable = false;
        public Set<String> newSelectedContactTypes = Collections.emptySet();
    }
}
