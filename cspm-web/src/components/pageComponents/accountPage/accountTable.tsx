'use client'

import { useEffect, useState } from 'react'
import { useTrigger } from '@/components/Zustand/store'
import Pagination from '@/components/pagination'
import ErrLogModal from '@/components/modal/errLogModal'
import { usePageination } from '@/components/Zustand/pageinationStore'
import FilterSection from '@/components/filterSection'
import {
  useCheckedItemsStore,
  useInputStore,
  useScanSet,
} from '@/components/Zustand/accountPageStore'
import AccountTableHead from '@/components/pageComponents/accountPage/table/accountTableHead'
import AccountFetchData from '@/components/pageComponents/accountPage/table/accountFetchData'
import AccountTableRow from '@/components/pageComponents/accountPage/table/accountTableRow'

/**
 * 패치한 데이터의 타입
 */
interface Props {
  client: string
  accountName: string
  accountId: string
  registerTime: string
  lastUpdateDescribeTime: string
  describeResult: string
}

export default function AccoountTable() {
  const { setScanClient, setScanAccountIdList, setScanAccountName, setScanScanReset } = useScanSet()
  const { fetchTrigger } = useTrigger()
  const { setInputAccountId, setInputAccountName, setInputReset } = useInputStore()
  const [data, setData] = useState<Props[]>([])

  AccountFetchData(setData)

  const { currentPage, itemsPerPage, setDataLength, setItemsPerPage, setCurrentPage } =
    usePageination()

  const indexOfLastItem = currentPage * itemsPerPage
  const indexOfFirstItem = indexOfLastItem - itemsPerPage
  const currentItems = data.slice(indexOfFirstItem, indexOfLastItem)

  useEffect(() => {
    setDataLength(data.length)
    setCurrentPage(1)
    setItemsPerPage(14)
    // eslint-disable-next-line react-hooks/exhaustive-deps
  }, [data])

  /**
   * showModal: 모달 보여주기
   * setShowModal: 모달 상태 변경
   */
  const [showModal, setShowModal] = useState(false)
  const [accountIdModal, setAccountIdModal] = useState('')
  const [scanTimeModal, setScanTimeModal] = useState('')
  const [accountNameModal, setAccountNameModal] = useState('')

  const handleToggleModal = (
    index: number,
    accountId: string,
    scanTime: string,
    accountName: string,
  ) => {
    setAccountIdModal(accountId)
    setScanTimeModal(scanTime)
    setShowModal(!showModal)
    setAccountNameModal(accountName)
  }

  /**
   * useCheckedItemsStore: 현제 체크한 아이템을 담아두는 zustand 저장소
   * checkedItems: 체크된 아이템
   * setCheckedItems: 체크된 아이템을 지정
   */
  const { checkedItems, setCheckedItems } = useCheckedItemsStore()
  const isItemChecked = (index: number) => checkedItems.has(index)
  const toggleCheckbox = (index: number) => {
    const newCheckedItems = new Set<number>(checkedItems)

    if (isItemChecked(index)) {
      newCheckedItems.delete(index)
    } else {
      newCheckedItems.add(index)
    }
    setCheckedItems(newCheckedItems)
  }

  const toggleAllCheckboxes = () => {
    if (checkedItems.size === currentItems?.length) {
      setCheckedItems(new Set<number>())
    } else {
      const newCheckedItems = new Set<number>()
      for (let i = 0; i < (currentItems?.length ?? 0); i++) {
        newCheckedItems.add(i)
      }
      setCheckedItems(newCheckedItems)
    }
  }

  useEffect(() => {
    // 데이터의 길이가 변경되면 실행
    const updatedCheckedItems = new Set(
      [checkedItems.values().next().value].filter((index) => index < (data?.length ?? 0)),
    )
    setCheckedItems(updatedCheckedItems)
    // eslint-disable-next-line react-hooks/exhaustive-deps
  }, [data.length])

  useEffect(() => {
    //체크된 아이템이 없다면 리셋
    if (checkedItems.size === 0) {
      setScanScanReset()
    }

    // 체크된 아이템이 업데이트될 때마다 실행
    if (checkedItems.size === 1) {
      const selectedIndex = checkedItems.values().next().value
      const selectedData = (data || [])[selectedIndex]

      // 삭제 데이터 리셋
      if (selectedData) {
        // selectedData가 존재하는지 확인
        setInputAccountId(selectedData.accountId)
        setInputAccountName(selectedData.accountName)
      } else {
        setInputReset() // selectedData가 없다면 입력 필드를 리셋
      }
    }

    // 삭제할때 보낼 accountId List
    const accountIdList = new Array<string>()
    const clientList = new Array<string>()
    const accountNameList = new Array<string>()
    checkedItems.forEach((index) => {
      const selectedData = (data || [])[index]
      if (selectedData) {
        // selectedData가 존재하는지 확인
        accountIdList.push(selectedData.accountId)
        clientList.push(selectedData.client)
        accountNameList.push(selectedData.accountName)
      }
    })

    //scan용, delete용 함수 담기
    setScanAccountIdList(accountIdList)
    setScanAccountName(accountNameList)
    setScanClient(clientList)

    // eslint-disable-next-line react-hooks/exhaustive-deps
  }, [checkedItems])

  /**
   * 계정 삭제시 체크박스 체크 풀기
   */
  useEffect(() => {
    setCheckedItems(new Set<number>())
  }, [fetchTrigger, setCheckedItems])

  return (
    <div className='mt-3 h-[700px] w-full overflow-hidden'>
      <div className='flex h-[35px] w-full items-center gap-4 text-sm text-black'>
        <span>Total List: {data.length}</span>
        <div>
          {' '}
          <FilterSection />
        </div>
      </div>
      <AccountTableHead
        toggleAllCheckboxes={toggleAllCheckboxes}
        allChecked={checkedItems.size === currentItems?.length}
      />
      <div className='h-[570px]'>
        {currentItems.length > 0 ? (
          currentItems.map((listIndex, index) => (
            <AccountTableRow
              key={index}
              index={index}
              listIndex={listIndex}
              isItemChecked={isItemChecked}
              toggleCheckbox={toggleCheckbox}
              handleToggleModal={handleToggleModal}
            />
          ))
        ) : (
          <div className='mt-4 flex items-center justify-center'>리스트가 없습니다</div>
        )}
      </div>
      <Pagination />
      <ErrLogModal
        showModal={showModal}
        setShowModal={setShowModal}
        accountId={accountIdModal}
        accountName={accountNameModal}
        scanTime={scanTimeModal}
      />
    </div>
  )
}
