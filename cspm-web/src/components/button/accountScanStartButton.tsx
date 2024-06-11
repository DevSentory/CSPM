'use client'

import { useCheckedItemsStore, useScanSet } from '@/components/Zustand/accountPageStore'
import { useTrigger } from '@/components/Zustand/store'
import Loading from '@/components/loading'
import ButtonLayer from '@/components/ui/buttonLayer'
import { useEffect, useState } from 'react'

const BASE_URL = process.env.NEXT_PUBLIC_NEXT_APP_BASE_URL

export default function AccountScanStartButton() {
  /**
   * 모달 창을 열고 닫 위해 사용
   * showModal: 모달창 상태
   * setShowModal: 모달창 상태 변경
   */
  const [showModal, setShowModal] = useState(false)

  /**
   * 테이블에서 선택된 데이터
   * checkedItems: 선택된 테이터 인텍스 배열
   * scanClient: 선택된 client 리스트
   * scanAccountIdList: 선택된 accountId 리스트
   * scanAccountName: 선택된 accountName 리스트
   */
  const { checkedItems } = useCheckedItemsStore()
  const { scanClient, scanAccountIdList, scanAccountName } = useScanSet()

  /**
   * 데이터 패치를 위해서 리스트로 담아두기
   * scanList: 테이블에서 담아둔 데이터를 꺼내서 리스트로 담아준다
   * scanStart: 스캔 시작 api 패치
   */
  const [dataResult, setDataResult] = useState('')
  const scanList = scanClient.map((client, index) => ({
    client,
    accountId: scanAccountIdList[index],
    accountName: scanAccountName[index],
  }))

  const scanStart = async () => {
    try {
      const response = await fetch(`${BASE_URL}/resources`, {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json; charset=utf-8',
        },
        body: JSON.stringify(scanList),
      })

      if (response.ok) {
        const inner = await response.json()
        const data = inner

        setDataResult(data.message)
      }
    } catch (error) {
      setDataResult('api 연결이 안됩니다.')
    }
  }

  /**
   * 테이블에 체크된 아이템이 없다면 alert 있다면 모달 열림
   */
  const handleShowModal = () => {
    {
      checkedItems.size > 0
        ? (setShowModal(true), setDataResult(''))
        : alert('테이블에서 선택해주세요')
    }
  }

  const { setFetchTrigger, repeatFetchTrigger, setRepeatFetchTrigger } = useTrigger()

  const handleCloseModal = () => {
    setDataResult('')
    setFetchTrigger()
    setRepeatFetchTrigger(false)
    setShowModal(false)
  }
  const handleScanStart = () => {
    scanStart()
    setRepeatFetchTrigger(true)
  }

  useEffect(() => {
    if (dataResult.length > 0) {
      setRepeatFetchTrigger(false)
    }
    // eslint-disable-next-line react-hooks/exhaustive-deps
  }, [dataResult])

  return (
    <div>
      <button
        className='h-9 w-24 rounded bg-kyboNavy text-white'
        onClick={() => {
          handleShowModal()
        }}
      >
        스캔 시작
      </button>

      {showModal && (
        <div className='fixed inset-0 z-50 flex items-center justify-center bg-dimGray'>
          <div className=' h-[250px] w-[400px] rounded-lg bg-gray-50 shadow-md'>
            <h5 className='text-start text-lg font-bold'>리소스 상세 조회</h5>

            <hr />
            {dataResult.length < 1 ? (
              <>
                <span className='flex h-36 w-full  w-full items-center justify-center text-black'>
                  {repeatFetchTrigger === true ? (
                    <div className='flex items-center justify-center'>
                      <Loading />
                    </div>
                  ) : (
                    `선택한 대상 ${checkedItems.size} 스캔을 시작 하시겠습니까?`
                  )}
                </span>
                <div className='flex justify-center gap-4 p-3'>
                  <ButtonLayer
                    buttonStyle='bg-kyboNavy w-16 px-4 py-2'
                    method={handleScanStart}
                    childText='시작'
                  />
                  <ButtonLayer
                    buttonStyle='bg-kyboNavy w-16 px-4 py-2'
                    method={handleCloseModal}
                    childText='취소'
                  />
                </div>
              </>
            ) : (
              <>
                <span className='flex h-36 w-full  w-full items-center justify-center text-black'>
                  {dataResult}
                </span>
                <div className='flex justify-center p-3'>
                  <button
                    className='flex items-center justify-center rounded-md bg-kyboNavy px-4 py-2 text-white hover:bg-gray-300'
                    onClick={() => {
                      handleCloseModal()
                    }}
                  >
                    확인
                  </button>
                </div>
              </>
            )}
          </div>
        </div>
      )}
    </div>
  )
}
