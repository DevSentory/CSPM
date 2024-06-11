'use client'

import { useCheckedItemsStore, useScanSet } from '@/components/Zustand/accountPageStore'
import { useTrigger } from '@/components/Zustand/store'
import { useState } from 'react'

const BASE_URL = process.env.NEXT_PUBLIC_NEXT_APP_BASE_URL

interface Props {
  showModal: boolean
  setShowModal: (newValue: boolean) => void
}

export default function DeleteModal({ showModal, setShowModal }: Props) {
  const { checkedItems } = useCheckedItemsStore()

  const [dataStatus, setDataStatus] = useState<String | null>(null)

  const { scanClient, scanAccountIdList, scanAccountName } = useScanSet()
  const deleteList = scanClient.map((client, index) => ({
    client,
    accountId: scanAccountIdList[index],
    accountName: scanAccountName[index],
  }))

  const UpdateFormData = async () => {
    try {
      const response = await fetch(`${BASE_URL}/accounts`, {
        method: 'DELETE',
        headers: {
          'Content-Type': 'application/json; charset=utf-8',
        },
        credentials: 'include',
        body: JSON.stringify(deleteList),
      })

      if (response.ok) {
        setDataStatus('SUCCESS')
      } else {
        setDataStatus('FAIL')
      }
    } catch (err) {
      setDataStatus('FAIL fetch Data')
    }
  }

  const { setFetchTrigger } = useTrigger()

  const handleToggleModal = () => {
    setShowModal(false)
    setDataStatus(null)
    setFetchTrigger()
  }

  return (
    <>
      {showModal && (
        <div className='fixed inset-0 z-50 flex items-center justify-center bg-dimGray'>
          <div className=' h-[250px] w-[400px] rounded-lg bg-gray-50 shadow-md'>
            <div className=' p-3 text-start'>
              <h5 className='text-lg font-bold text-black'>대상 삭제</h5>
            </div>
            <hr />

            {dataStatus === null ? (
              <>
                <span className='flex h-[100px] w-full items-center justify-center text-black'>
                  {checkedItems.size} 대상을 삭제하시겠습니까?
                </span>
                <div className='mt-4 flex items-center justify-center space-x-5'>
                  <div
                    className='flex items-center justify-center rounded-md bg-kyboNavy px-4 py-2 text-black text-white hover:bg-gray-300'
                    onClick={UpdateFormData}
                  >
                    삭제
                  </div>
                  <button
                    className='flex items-center justify-center rounded-md bg-kyboNavy px-4 py-2 text-white hover:bg-gray-300'
                    onClick={handleToggleModal}
                  >
                    취소
                  </button>
                </div>
              </>
            ) : (
              <>
                <div className='flex h-[100px] w-full items-center justify-center text-black'>
                  {dataStatus}
                </div>
                <div className='mt-4 flex items-center justify-center space-x-5'>
                  <button
                    className='flex items-center justify-center rounded-md bg-kyboNavy px-4 py-2 text-white hover:bg-gray-300'
                    onClick={handleToggleModal}
                  >
                    확인
                  </button>
                </div>
              </>
            )}
          </div>
        </div>
      )}
    </>
  )
}
